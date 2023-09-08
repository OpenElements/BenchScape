# BenchScape Application Architecture and Communication

## Overview

BenchScape is a web application that consists of a React frontend and a Spring Boot backend. It allows users to interact with benchmark data. Here's how the frontend and backend servers communicate:

## React Frontend

`Root Directory:` The root directory of the React frontend is located at `BenchScape/jmh-frontend/src/main/frontend`.

## Frontend Responsibilities

`UI Presentation:` The React frontend is responsible for rendering the user interface (UI) of the application. It displays benchmark data in a user-friendly format.

`API Requests:` The frontend communicates with the Spring Boot backend by sending HTTP requests to predefined API endpoints.

## Communication with Backend

`API URL Configuration:` The React frontend uses environment variables (defined in .env.development and .env.production) to configure the API URL. This allows it to target the correct backend server based on the development or production environment.

`Fetch Data:` When the frontend component loads or when a user interacts with the application, it makes HTTP requests (e.g., GET, POST) to the Spring Boot backend's API endpoints.

`Receive and Display Data:` The frontend receives JSON data from the backend as responses to its API requests. It then processes this data and displays it in a user-friendly format, such as a table.

## Spring Boot Backend

`Backend Code:` The Spring Boot backend code is responsible for handling data storage, business logic, and API endpoints.

## Backend Responsibilities

`Data Storage:` The backend stores benchmark data, quality gate checks, and other relevant information.

`Business Logic:` It performs business logic operations, such as saving benchmark data, retrieving benchmark definitions, and applying quality gate checks.

`API Endpoints:` The backend exposes RESTful API endpoints (e.g., /api/benchmark) that the frontend can interact with.

## Communication with Frontend

`RESTful API:` The Spring Boot backend listens for incoming HTTP requests from the frontend at predefined API endpoints. It processes these requests and sends appropriate responses.

`Response Format:` The backend typically responds to frontend requests with JSON data. For example, when the frontend requests a list of benchmarks, the backend sends JSON-formatted benchmark data.
