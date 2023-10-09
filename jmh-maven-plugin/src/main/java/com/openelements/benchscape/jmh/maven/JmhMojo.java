package com.openelements.benchscape.jmh.maven;

import com.openelements.benchscape.jmh.client.JmhRunnerCommand;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ResolutionErrorHandler;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;

/**
 * Mojo that executes the benchmarks. Heavily inspired by the JMH Maven plugin
 * (https://github.com/metlos/jmh-maven-plugin)
 */
@Mojo(name = "jmh", defaultPhase = LifecyclePhase.VERIFY, requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM, requiresDependencyCollection = ResolutionScope.RUNTIME_PLUS_SYSTEM)
public class JmhMojo extends AbstractMojo {

    private final static String MY_GROUP_ID = "com.open-elements.benchscape";

    private final static String MY_ARTIFACT_ID = "jmh-maven-plugin";

    private final static String JMH_CLIENT_ARTIFACT_ID = "jmh-client";

    public static final String META_INF = "META-INF";

    public static final String BENCHMARK_LIST_FILE = "BenchmarkList";

    public static final String JAVA_PROCESS = "java";

    public static final String BIN_FOLDER = "bin";

    public static final String JAVA_HOME = "java.home";
    public static final String CLASSPATH_ARG = "-cp";
    public static final String JAR_TYPE = "jar";

    /**
     * If true benchmarks in test scope will be executed (Java classes of benchmarks must be placed under
     * {@code src/main/java}).
     */
    private boolean benchmarksInTestScope = false;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "true")
    private boolean writeToFile;

    @Parameter(defaultValue = "${project.build.directory}/jmh-results.json")
    private String file;

    @Parameter(defaultValue = "true")
    private boolean upload;

    @Parameter(defaultValue = "https://backend.benchscape.cloud")
    private String url;

    @Component
    private ArtifactHandlerManager artifactHandlerManager;

    @Component
    private RepositorySystem repositorySystem;

    @Component
    private ResolutionErrorHandler resolutionErrorHandler;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Objects.requireNonNull(project, "project must not be null");
            Objects.requireNonNull(session, "session must not be null");
            Objects.requireNonNull(artifactHandlerManager, "artifactHandlerManager must not be null");
            Objects.requireNonNull(repositorySystem, "repositorySystem must not be null");
            Objects.requireNonNull(resolutionErrorHandler, "resolutionErrorHandler must not be null");

            final File benchmarkList = getBenchmarkList();
            getLog().debug("Checking if '" + benchmarkList + "' exists.");
            if (!benchmarkList.exists()) {
                getLog().info("No JMH benchmarks found, skipping execution of goal.");
            } else {
                if (getLog().isDebugEnabled()) {
                    getLog().debug("Benchmark list:");
                    Files.readAllLines(benchmarkList.toPath(), StandardCharsets.UTF_8).forEach(l -> getLog().debug(l));
                }

                getLog().debug("Creating classpath for JMH execution");
                final List<String> classpath = getClasspathForBenchmarkRun();

                getLog().debug("Executing benchmarks run.");
                final int exitCode = executeBenchmarkRunInForkedProcess(classpath);
                if (exitCode != 0) {
                    throw new RuntimeException("The process failed with non-zero exit code: " + exitCode);
                }
            }
        } catch (final Exception e) {
            throw new MojoExecutionException("Error while executing goal.", e);
        }
    }

    /**
     * Returns a list of all dependencies that are required for the execution of the benchmarks.
     *
     * @return list of all dependencies that are required for the execution of the benchmarks
     * @throws DependencyResolutionRequiredException if the dependencies could not be resolved
     */
    @NonNull
    private List<String> getClasspathForBenchmarkRun() throws DependencyResolutionRequiredException {
        getLog().debug("Trying to extract version of plugin.");
        final String myVersion = getMyVersion();
        getLog().debug("Version of plugin is " + myVersion);

        getLog().debug("Trying to resolve artifact of '" + MY_GROUP_ID + ":" + JMH_CLIENT_ARTIFACT_ID + ":"
                + myVersion + "'.");
        final List<String> dependencies = resolveArtifact(MY_GROUP_ID, JMH_CLIENT_ARTIFACT_ID, myVersion);
        getLog().debug("All dependencies for '" + MY_GROUP_ID + ":" + JMH_CLIENT_ARTIFACT_ID + ":" + myVersion
                + "' resolved:");
        dependencies.forEach(d -> getLog().debug(d));

        final List<String> classpath = new ArrayList<>();
        classpath.addAll(project.getRuntimeClasspathElements());
        if (benchmarksInTestScope) {
            classpath.addAll(project.getTestClasspathElements());
        }
        classpath.addAll(dependencies);
        getLog().debug("Classpath for JMH execution defined:");
        classpath.forEach(c -> getLog().debug(c));
        return classpath;
    }

    /**
     * Executes the benchmark run in a forked process and returns the exit code of the process.
     *
     * @param classpath classpath for the forked process
     * @return exit code of the forked process
     * @throws IOException          if the forked process could not be started
     * @throws InterruptedException if the forked process was interrupted
     */
    private int executeBenchmarkRunInForkedProcess(@NonNull final List<String> classpath)
            throws IOException, InterruptedException {
        final String javaHome = System.getProperty(JAVA_HOME);
        final String javaProcess = javaHome + File.separator + BIN_FOLDER + File.separator + JAVA_PROCESS;
        getLog().debug("Java process is '" + javaProcess + "'.");
        getLog().debug("PARAM 'writeToFile'=" + writeToFile);
        getLog().debug("PARAM 'file'=" + file);
        getLog().debug("PARAM 'upload'=" + upload);
        getLog().debug("PARAM 'url'=" + url);

        final List<String> command = new ArrayList<>();
        command.add(javaProcess);
        command.add(CLASSPATH_ARG);
        command.add(String.join(File.pathSeparator, classpath));
        command.add(JmhRunnerCommand.class.getName());
        if (writeToFile) {
            command.add("--write");
        }
        command.add("--path");
        command.add(file);
        if (upload) {
            command.add("--upload");
        }
        command.add("--url");
        command.add(url);
        getLog().debug("Running forked execution using: " + command);

        final ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(project.getBasedir());
        processBuilder.redirectErrorStream(true);
        final Process process = processBuilder.start();
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(l -> getLog().debug(l));
        }
        final int exitCode = process.waitFor();
        return exitCode;
    }

    /**
     * Returns the version of the plugin that is currently executed.
     *
     * @return the version of the plugin that is currently executed
     */
    @NonNull
    private String getMyVersion() {
        return project.getBuildPlugins().stream()
                .filter(d -> d.getGroupId().equals(MY_GROUP_ID) && d.getArtifactId().equals(MY_ARTIFACT_ID))
                .map(d -> d.getVersion()).findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find version of plugin."));
    }

    /**
     * Resolves the artifact that is defined by given coordinates and returns a list that contains the paths of the
     * resolved artifact and all its dependencies.
     *
     * @param groupId    the group id of the artifact
     * @param artifactId the artifact id of the artifact
     * @param version    the version of the artifact
     * @return the paths resolved artifact and all its dependencies
     */
    @NonNull
    private List<String> resolveArtifact(@NonNull final String groupId, @NonNull final String artifactId,
            @NonNull final String version) {
        Objects.requireNonNull(groupId, "groupId must not be null.");
        Objects.requireNonNull(artifactId, "artifactId must not be null.");
        Objects.requireNonNull(version, "version must not be null.");
        final Artifact artifact = new DefaultArtifact(groupId, artifactId,
                VersionRange.createFromVersion(version), Artifact.SCOPE_RUNTIME, JAR_TYPE, null,
                artifactHandlerManager.getArtifactHandler(JAR_TYPE), false);

        final ArtifactResolutionRequest request = new ArtifactResolutionRequest().setArtifact(artifact)
                .setResolveRoot(true)
                .setResolveTransitively(true).setLocalRepository(session.getLocalRepository())
                .setRemoteRepositories(project.getRemoteArtifactRepositories());

        final ArtifactResolutionResult resolutionResult = repositorySystem.resolve(request);

        try {
            resolutionErrorHandler.throwErrors(request, resolutionResult);
        } catch (final ArtifactResolutionException e) {
            getLog().error(e.getMessage());
            throw new IllegalStateException("Failed to resolve artifact for '" + groupId + ":" + artifactId + ":"
                    + version + "'", e);
        }

        final List<String> elements = new ArrayList<>(resolutionResult.getArtifacts().size());
        for (final Artifact resolved : resolutionResult.getArtifacts()) {
            final String path = resolved.getFile().getAbsolutePath();
            elements.add(path);
        }
        return elements;
    }

    /**
     * Returns the file that contains the list of all benchmarks (as defined by JMH).
     *
     * @return the file that contains the list of all benchmarks (as defined by JMH)
     */
    @NonNull
    private File getBenchmarkList() {
        if (benchmarksInTestScope) {
            return new File(new File(project.getBuild().getTestOutputDirectory(), META_INF),
                    BENCHMARK_LIST_FILE);
        } else {
            return new File(new File(project.getBuild().getOutputDirectory(), META_INF),
                    BENCHMARK_LIST_FILE);
        }
    }
}
