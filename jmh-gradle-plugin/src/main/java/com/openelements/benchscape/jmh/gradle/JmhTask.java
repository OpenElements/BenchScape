package com.openelements.benchscape.jmh.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public abstract class JmhTask extends DefaultTask {

    /**
     * The file that contains the list of all benchmarks (as defined by JMH).
     */
    @InputFile
    public abstract RegularFileProperty getBenchmarkList();

    @Input
    public abstract Property<Boolean> getUpload();

    @Input
    public abstract Property<String> getUrl();

    @Input
    @Optional
    public abstract Property<String> getApiPrincipal();

    @Input
    @Optional
    public abstract  Property<String> getApiKey();

    @Classpath
    public abstract ConfigurableFileCollection getClasspath();

    @OutputDirectory
    public abstract DirectoryProperty getDestinationDir();

    @Inject
    protected abstract ExecOperations getExecOperations();

    @TaskAction
    public void execute() {
        final File benchmarkList = getBenchmarkList().get().getAsFile();
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Benchmark list:");
            try {
                Files.readAllLines(benchmarkList.toPath(), StandardCharsets.UTF_8)
                        .forEach(l -> getLogger().debug(l));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        getLogger().debug("Executing benchmarks run.");
        executeBenchmarkRunInForkedProcess();
    }

    /**
     * Executes the benchmark run in a forked process and returns the exit code of the process.
     */
    private void executeBenchmarkRunInForkedProcess() {
        getLogger().debug("PARAM 'file'=" + getDestinationDir().get().getAsFile().getAbsolutePath());
        getLogger().debug("PARAM 'upload'=" + getUpload().get());
        getLogger().debug("PARAM 'url'=" + getUrl().get());
        getLogger().debug("PARAM 'apiPrincipal'=" + getApiPrincipal().getOrElse(""));
        getLogger().debug("PARAM 'apiKey'=" + getApiKey().getOrElse(""));

        final List<String> args = new ArrayList<>();

        args.add("--write");
        if (getUpload().get()) {
            args.add("--upload");
        }
        args.add("--path");
        args.add(getDestinationDir().get().getAsFile().getAbsolutePath());
        args.add("--url");
        args.add(getUrl().get());

        if (getApiPrincipal().isPresent()) {
            args.add("--apiPrincipal");
            args.add(getApiPrincipal().get());
        }
        if (getApiKey().isPresent()) {
            args.add("--apiKey");
            args.add(getApiKey().get());
        }
        getLogger().debug("Running forked execution with arguments: " + args);

        getExecOperations().javaexec(p -> {
            p.classpath(getClasspath());
            p.getMainClass().set("com.openelements.benchscape.jmh.client.JmhRunnerCommand");
            p.args(args);
        });
    }
}
