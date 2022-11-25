# JMH Store

The JMH Store project contains functionality to collect 
[Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh) results for continuous 
integration approaches.


# Overview

The project contains the `jmh-store` module that acts as a server to store JMH results. The server
provides a minimalistic frontend to show the JMH results as timeseries. Next to this the server
provides an endpoint for the [JSON plugin for Grafana](https://grafana.com/grafana/plugins/simpod-json-datasource/).

JMH results can be created and uploaded automatically by using the Maven Plugin in the 
`jmh-maven-plugin` module or by calling the Uploader directly (see `jmh-runner` module).