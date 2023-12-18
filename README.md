# BenchScape

BenchScape contains functionality to collect and visualize [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh)
results for continuous integration approaches.

## Overview

The project contains the `server` module that acts as a server to store JMH results.

The project contains the `frontend` module that acts as a web frontend for the server.

JMH results can be created and uploaded automatically by using the Maven Plugin in the
`jmh-maven-plugin` module or by calling the Uploader directly (see `jmh-client` module).

## Building the project

The project is based on [Java 17](https://adoptium.net/de/temurin/releases/)
and [Maven](https://maven.apache.org). You do not need to have a global Maven installation to build
the project since the [Maven Wrapper](https://maven.apache.org/wrapper/) is used to provide a local
Maven executable for the repository.

To build the project just call `./mvnw verify` from the root folder of the
project (`mvnw.cmd verify` on windows).

## Running the project

The project contains several modules that can be run independently. The following sections describe how to run the
different modules / parts of the project.

### Starting the backend server

The `server` module contains the Spring Boot based server that can simply be started by the main
class `com.openelements.benchscape.server.app.Application`. The server provides a minimalistic frontend to show the JMH results
as timeseries. Once the server is started the frontend can be reached at [http://localhost:8080](http://localhost:8080).
Currently, the server automatically creates some dummy data for 3 benchmarks at start time.Next to that the server
provides a swagger-ui at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) and
an endpoint for the [JSON plugin for Grafana](https://grafana.com/grafana/plugins/simpod-json-datasource/) at `/grafana`.

### Starting the frontend server

Open a new terminal or command prompt (keep the backend server running in the previous terminal).
Navigate to the root directory of your React frontend project (`frontend`). You can use the cd command to change your
working directory:

```
cd frontend
```

The required Node.js packages are already installed by the `./mvnw verify` run.
You can update the frontend dependencies anytime with the following command instead of doing the full maven call:

```
npm install
```

Start the React development server:

```
npm start
```

The Frontend development server should start and be accessible at http://localhost:3000.

## Communication between Frontend and Backend

`API URL Configuration:` The React frontend uses environment variables (defined in .env.development and .env.production) to configure the API URL. This allows it to target the correct backend server based on the development or production environment.

`Fetch Data:` When the frontend component loads or when a user interacts with the application, it makes HTTP requests (e.g., GET, POST) to the Spring Boot backend's API endpoints.

### Scenario A: Fetching Data from a Local Backend

To run the frontend and have it fetch data from a backend hosted on your local machine, follow these steps:

- Ensure that you have the backend server running locally on your machine, typically on localhost or a specific port (e.g., http://localhost:8080).
- In your frontend project directory `cd frontend`, locate the `.env.development` file. This file contains environment-specific configuration settings.
- Set the API URL in `.env.development` to point to your local backend server. For example: `REACT_APP_API_URL=http://localhost:8080/api`
- Start the React frontend as you normally would for development (using npm start). The frontend will now make API requests to your local backend server.
- We are using dotenv as described at https://create-react-app.dev/docs/adding-custom-environment-variables/#what-other-env-files-can-be-used. The set different environments we use env-cmd (https://github.com/toddbluhm/env-cmd) and custom npm scripts (see `package.json`)

### Scenario B: Fetching Data from a Cloud-Based Backend

If you want to run the frontend and have it fetch data from a backend hosted in the cloud, follow these steps:

- Ensure that your cloud-based backend is accessible and has a publicly available API endpoint.
- In your project directory, locate the `.env.production` file. This file contains environment-specific configuration settings for production.
- Set the API URL in `.env.production` to point to your cloud-based backend's API endpoint. For example: `REACT_APP_API_URL=https://your-cloud-backend.com/api`
- Start the React frontend for production deployment (e.g., using npm build and a suitable web server). The frontend will now make API requests to your cloud-based backend.
- We are using dotenv as described at https://create-react-app.dev/docs/adding-custom-environment-variables/#what-other-env-files-can-be-used. The set different environments we use env-cmd (https://github.com/toddbluhm/env-cmd) and custom

`Receive and Display Data:` The frontend receives JSON data from the backend as responses to its API requests. It then processes this data and displays it in a user-friendly format, such as a table.

### Using the Maven plugin

The `jmh-maven-plugin` module contains a Maven plugin that can be used to execute JMH benchmark and
upload its results the JMH Store. The `sample` module shows how a Maven project should be configured
to execute JMH benchmarks and upload results. You can have a look at the `<build>` tag of
the `pom.xml` of the module to see how the integration is working. Since the plugin is bound to the
`verify` phase you can easily trigger a full execution by calling `./mvnw verify` on the root level
of the repo (see build instruction) once the JMH Store server is running.

## Azure deployment for main branch

The `main` branch is automatically deployed to Microsoft Azure.
Any change on the main branch will trigger an update to Azure.

- The server instance can be found at https://backend.benchscape.cloud
- The frontend of the app can be found at https://app.benchscape.cloud
- Swagger-UI for the instance can be found at https://benchscape.azurewebsites.net/swagger-ui/index.html

The workflow is defined by the `deploy-main-to-azure.yml` [GitHub Action](https://github.com/OpenElements/BenchScape/actions/workflows/deploy-main-to-azure.yml).

## Azure deployment for PRs

The branch of a PR will not automatically be deployed to Azure to save costs.
Instead, a GitHub Action can be triggered manually to deploy the server to Azure.

- The server instance can be found at http://backend.integration.benchscape.cloud
- The frontend of the app can be found at https://app.integration.benchscape.cloud
- Swagger-UI for the instance can be found at https://benchscape-integration.azurewebsites.net/swagger-ui/index.html

The workflow is defined by the `deploy-integration-to-azure.yml` [GitHub Action](https://github.com/OpenElements/BenchScape/actions/workflows/deploy-integration-to-azure.yml).

## Services used for operation

A [Spring-Boot-Admin](https://github.com/codecentric/spring-boot-admin) instance is running at https://open-elements-spring-admin.azurewebsites.net/applications

## REST API

The server provides a REST API to push / get data. The following should give an overview of the different endpoints. To test the endpoints the swagger-ui at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) can be used.

- `SERVER-URL/api/v2/execution` The endpoint is used by the maven plugin / the JMH-client to upload new benchmark data.
- `SERVER-URL/api/v2/benchmark/all` The endpoint returns all benchmarks (only the basoic metadata and not any measurements)
- `SERVER-URL/api/v2/environment` The endpoints for the new environment dialogs & data. An environment can be created or deleted.
- `SERVER-URL/api/v2/environment/all` Returns the basic metadata of all environments
- `SERVER-URL/api/v2/environment/find` Search for environments
- `SERVER-URL/api/v2/measurement/find` Find all measurements for a specific benchmark (and optional environments) (within an optional range)
- `SERVER-URL/api/v2/measurement/metadata` Get the additional metadata for a given measurement entry
- `SERVER-URL/api/v2/debug/*` Some endpoints for debugging / testing that just always returns all available data entries
