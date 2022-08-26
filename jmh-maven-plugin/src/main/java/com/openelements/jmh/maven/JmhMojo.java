package com.openelements.jmh.maven;

import com.openelements.jmh.store.data.runner.JmhUploader;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;

@Mojo(name = "java", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST)
public class JmhMojo extends AbstractMojo {

    /**
     * The enclosing project.
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * This folder is added to the list of those folders containing source to be compiled. Use this if your plugin
     * generates source code.
     */
    @Parameter(property = "sourceRoot")
    private File sourceRoot;

    /**
     * This folder is added to the list of those folders containing source to be compiled for testing. Use this if your
     * plugin generates test source code.
     */
    @Parameter(property = "testSourceRoot")
    private File testSourceRoot;

    /**
     * Defines the scope of the classpath passed to the plugin. Set to compile,test,runtime or system depending on your
     * needs. Since 1.1.2, the default value is 'runtime' instead of 'compile'.
     */
    @Parameter(property = "exec.classpathScope", defaultValue = "runtime")
    private String classpathScope;

    /**
     * Add project resource directories to classpath. This is especially useful if the exec plugin is used for a code
     * generator that reads its settings from the classpath.
     *
     * @since 1.5.1
     */
    @Parameter(property = "addResourcesToClasspath", defaultValue = "false")
    private boolean addResourcesToClasspath;

    /**
     * Add project output directory to classpath. This might be undesirable when the exec plugin is run before the
     * compile step. Default is <code>true</code>.
     *
     * @since 1.5.1
     */
    @Parameter(property = "addOutputToClasspath", defaultValue = "true")
    private boolean addOutputToClasspath;

    /**
     * Execute goal.
     *
     * @throws MojoExecutionException execution of the main class or one of the threads it generated failed.
     * @throws MojoFailureException   something bad happened...
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        final IsolatedThreadGroup threadGroup = new IsolatedThreadGroup(getLog(), "JmhUploader");
        final URLClassLoader classLoader = getClassLoader();
        final IsolatedThreadFactory threadFactory = new IsolatedThreadFactory(threadGroup, classLoader);
        Executors.newSingleThreadExecutor(threadFactory).submit(() -> {
            try {
                executeJmh();
            } catch (final Throwable e) {
                Thread.currentThread().getThreadGroup().uncaughtException(Thread.currentThread(), e);
            }
        });
        joinNonDaemonThreads(threadGroup);
        terminateFullThreadGroup(threadGroup);

        if (classLoader != null) {
            try {
                classLoader.close();
            } catch (IOException e) {
                getLog().error(e.getMessage(), e);
            }
        }
        final Throwable uncaughtException = threadGroup.getUncaughtException();
        if (uncaughtException != null) {
            throw new MojoExecutionException("An exception occurred while executing the Java class. "
                    + uncaughtException.getMessage(), uncaughtException);
        }
    }

    private void executeJmh() throws Throwable {
        Class<?> bootClass = Thread.currentThread().getContextClassLoader().loadClass(JmhUploader.class.getName());
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle constructorHandle = lookup.findConstructor(bootClass, MethodType.methodType(void.class));
        Object uploader = constructorHandle.invoke();
        MethodHandle run = lookup.findVirtual(bootClass, "run", MethodType.methodType(void.class));
        run.invoke(uploader);
    }

    private boolean containsDaemon(Collection<Thread> threads) {
        return Objects.requireNonNull(threads).stream()
                .filter(t -> t.isDaemon())
                .findAny()
                .isPresent();
    }

    private boolean containsDaemon(ThreadGroup threadGroup) {
        return containsDaemon(getActiveThreads(threadGroup));
    }

    private void joinNonDaemonThreads(ThreadGroup threadGroup) {
        while (containsDaemon(threadGroup)) {
            getActiveThreads(threadGroup).stream()
                    .filter(t -> !t.isDaemon())
                    .forEach(t -> joinThread(thread, 0));
        }
    }

    private void joinThread(Thread thread) {
        try {
            getLog().debug("Joining on thread " + thread.getName());
            thread.join(0);
        } catch (final InterruptedException e) {
            getLog().warn("Interrupted after joining timeout for thread " + thread.getName());
        }
    }

    private void terminateFullThreadGroup(ThreadGroup threadGroup) {
        long startTime = System.currentTimeMillis();
        Set<Thread> uncooperativeThreads = new HashSet<>(); // these were not responsive to interruption
        for (Collection<Thread> threads = getActiveThreads(threadGroup); !threads.isEmpty(); threads =
                getActiveThreads(threadGroup), threads.removeAll(uncooperativeThreads)) {
            // Interrupt all threads we know about as of this instant (harmless if spuriously went dead (! isAlive())
            // or if something else interrupted it ( isInterrupted() ).
            for (Thread thread : threads) {
                getLog().debug("interrupting thread " + thread);
                thread.interrupt();
            }
            // Now join with a timeout and call stop() (assuming flags are set right)
            for (Thread thread : threads) {
                if (!thread.isAlive()) {
                    continue; // and, presumably it won't show up in getActiveThreads() next iteration
                }
                joinThread(thread, 1_000);

                if (!thread.isAlive()) {
                    continue;
                }
                uncooperativeThreads.add(thread); // ensure we don't process again
                getLog().warn("thread " + thread + " will be Thread.stop()'ed");
                thread.stop();
            }
        }
        if (!uncooperativeThreads.isEmpty()) {
            getLog().warn("NOTE: " + uncooperativeThreads.size() + " thread(s) did not finish despite being asked to"
                    + " via interruption. This is not a problem with exec:java, it is a problem with the running code."
                    + " Although not serious, it should be remedied.");
        } else {
            int activeCount = threadGroup.activeCount();
            if (activeCount != 0) {
                // TODO this may be nothing; continue on anyway; perhaps don't even log in future
                Thread[] threadsArray = new Thread[1];
                threadGroup.enumerate(threadsArray);
                getLog().debug("strange; " + activeCount + " thread(s) still active in the group " + threadGroup
                        + " such as " + threadsArray[0]);
            }
        }
        try {
            threadGroup.destroy();
        } catch (IllegalThreadStateException e) {
            getLog().warn("Couldn't destroy threadgroup " + threadGroup, e);
        }
    }

    private Collection<Thread> getActiveThreads(ThreadGroup threadGroup) {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        int numThreads = threadGroup.enumerate(threads);
        Collection<Thread> result = new ArrayList<Thread>(numThreads);
        for (int i = 0; i < threads.length && threads[i] != null; i++) {
            result.add(threads[i]);
        }
        return result; // note: result should be modifiable
    }

    /**
     * Set up a classloader for the execution of the main class.
     *
     * @return the classloader
     * @throws MojoExecutionException if a problem happens
     */
    private URLClassLoader getClassLoader()
            throws MojoExecutionException {
        List<Path> classpathURLs = new ArrayList<>();
        this.addRelevantProjectDependenciesToClasspath(classpathURLs);
        try {
            return new URLClassLoader(classpathURLs.stream().map(p -> {
                try {
                    return p.toUri().toURL();
                } catch (MalformedURLException e) {
                    throw new RuntimeException("ERROR", e);
                }
            }).toArray(i -> new URL[i]));
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }

    /**
     * Add any relevant project dependencies to the classpath. Takes includeProjectDependencies into consideration.
     *
     * @param path classpath of {@link java.net.URL} objects
     * @throws MojoExecutionException if a problem happens
     */
    private void addRelevantProjectDependenciesToClasspath(List<Path> path)
            throws MojoExecutionException {
        getLog().debug("Project Dependencies will be included.");

        List<Artifact> artifacts = new ArrayList<>();
        List<Path> theClasspathFiles = new ArrayList<>();

        collectProjectArtifactsAndClasspath(artifacts, theClasspathFiles);

        for (Path classpathFile : theClasspathFiles) {
            getLog().debug("Adding to classpath : " + classpathFile);
            path.add(classpathFile);
        }

        for (Artifact classPathElement : artifacts) {
            getLog().debug("Adding project dependency artifact: " + classPathElement.getArtifactId()
                    + " to classpath");
            path.add(classPathElement.getFile().toPath());
        }

    }

    /**
     * Collects the project artifacts in the specified List and the project specific classpath (build output and build
     * test output) Files in the specified List, depending on the plugin classpathScope value.
     *
     * @param artifacts         the list where to collect the scope specific artifacts
     * @param theClasspathFiles the list where to collect the scope specific output directories
     */
    private void collectProjectArtifactsAndClasspath(List<Artifact> artifacts, List<Path> theClasspathFiles) {
        if (addResourcesToClasspath) {
            for (Resource r : project.getBuild().getResources()) {
                theClasspathFiles.add(Paths.get(r.getDirectory()));
            }
        }

        if ("compile".equals(classpathScope)) {
            artifacts.addAll(project.getCompileArtifacts());
            if (addOutputToClasspath) {
                theClasspathFiles.add(Paths.get(project.getBuild().getOutputDirectory()));
            }
        } else if ("test".equals(classpathScope)) {
            artifacts.addAll(project.getTestArtifacts());
            if (addOutputToClasspath) {
                theClasspathFiles.add(Paths.get(project.getBuild().getTestOutputDirectory()));
                theClasspathFiles.add(Paths.get(project.getBuild().getOutputDirectory()));
            }
        } else if ("runtime".equals(classpathScope)) {
            artifacts.addAll(project.getRuntimeArtifacts());
            if (addOutputToClasspath) {
                theClasspathFiles.add(Paths.get(project.getBuild().getOutputDirectory()));
            }
        } else if ("system".equals(classpathScope)) {
            artifacts.addAll(project.getSystemArtifacts());
        } else {
            throw new IllegalStateException("Invalid classpath scope: " + classpathScope);
        }

        getLog().debug("Collected project artifacts " + artifacts);
        getLog().debug("Collected project classpath " + theClasspathFiles);
    }

}
