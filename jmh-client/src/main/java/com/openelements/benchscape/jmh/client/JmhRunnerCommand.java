package com.openelements.benchscape.jmh.client;

import com.openelements.benchscape.jmh.client.io.RestHandlerConfig;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "jmh-runner", mixinStandardHelpOptions = true, version = "0.1",
        description = "Executes JMH benchmarks on classpath and handles the results")
public class JmhRunnerCommand implements Callable<Integer> {

    @Option(names = {"--upload"}, description = "Whether to upload the results to a server")
    private boolean uploadResults;

    @Option(names = {"--write"}, description = "Whether to write the results to disc")
    private boolean writeResults;

    @Option(names = {
            "--path"}, description = "The path of the directory to write the results to. If null, the default directory is used")
    private Path resultsPath;

    @Parameters(paramLabel = "INCLUDES", description = "The names of the benchmarks to run. If empty, all benchmarks are run")
    private Collection<String> includes;

    @Option(names = {
            "--url"}, description = "The base URL of the server to post the results to. If null, the default server is used")
    private String uploadBaseUrl;

    @Option(names = {
            "--apiPrincipal"}, description = "TODO")
    private String apiPrincipal;

    @Option(names = {
            "--apiKey"}, description = "TODO")
    private String apiKey;

    @Override
    public Integer call() {
        try {
            new JmhRunner(uploadResults, writeResults, resultsPath,
                    new RestHandlerConfig(uploadBaseUrl, apiPrincipal, apiKey), includes).execute();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new JmhRunnerCommand()).execute(args);
    }
}
