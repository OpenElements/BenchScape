package com.openelements.jmh.maven;

import com.openelements.benchscape.jmh.client.JmhUploader;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "jmh", defaultPhase = LifecyclePhase.VERIFY, requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM, requiresDependencyCollection = ResolutionScope.RUNTIME_PLUS_SYSTEM)
public class JmhMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

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
        Future<?> submit = Executors.newSingleThreadExecutor(threadFactory).submit(() -> {
            try {
                executeJmh();
            } catch (final Throwable e) {
                Thread.currentThread().getThreadGroup().uncaughtException(Thread.currentThread(), e);
            }
        });

        try {
            submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

    private void joinNonDaemonThreads(final ThreadGroup threadGroup) {
        Objects.requireNonNull(threadGroup);
        while (containsDaemon(threadGroup)) {
            getActiveThreads(threadGroup).stream()
                    .filter(t -> !t.isDaemon())
                    .forEach(t -> joinThread(t));
        }
    }

    private void joinThread(final Thread thread) {
        Objects.requireNonNull(thread);
        try {
            getLog().debug("Joining on thread " + thread.getName());
            thread.join(0);
        } catch (final InterruptedException e) {
            getLog().warn("Interrupted after joining timeout for thread " + thread.getName());
        }
    }

    private void terminateFullThreadGroup(final ThreadGroup threadGroup) {
        Objects.requireNonNull(threadGroup);
        getActiveThreads(threadGroup).stream().forEach(thread -> {
            getLog().debug("Interrupting thread " + thread);
            thread.interrupt();
            try {
                thread.join(1_000);
            } catch (final InterruptedException ignore) {

            }
            if (thread.isAlive()) {
                getLog().warn("Thread " + thread + " will be Thread.stop()'ed");
                thread.stop();
            }
        });
        final long stillRunningCount = getActiveThreads(threadGroup).stream()
                .filter(t -> t.isAlive()).count();
        if (stillRunningCount > 0) {
            getLog().warn(stillRunningCount + " threads can not be stopped");
        }
    }

    private List<Thread> getActiveThreads(final ThreadGroup threadGroup) {
        Objects.requireNonNull(threadGroup);
        final Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        return Arrays.stream(threads).collect(Collectors.toList());
    }

    /**
     * Set up a classloader for the execution of the main class.
     *
     * @return the classloader
     * @throws MojoExecutionException if a problem happens
     */
    private URLClassLoader getClassLoader() throws MojoExecutionException {
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
    private void addRelevantProjectDependenciesToClasspath(final List<Path> path) throws MojoExecutionException {
        Objects.requireNonNull(path);

        List<Path> resources = project.getBuild().getResources().stream()
                .map(resource -> Paths.get(resource.getDirectory()))
                .collect(Collectors.toList());
        path.addAll(resources);
        path.add(Paths.get(project.getBuild().getOutputDirectory()));

        List<Path> dependencies = project.getArtifacts().stream()
                .map(artifact -> artifact.getFile().toPath())
                .collect(Collectors.toList());
        path.addAll(dependencies);

        path.stream().forEach(element -> getLog().info("Adding to JMH Classpath: " + element));
    }
}
