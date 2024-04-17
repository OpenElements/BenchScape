#!/bin/bash

# Build Spring Boot server
echo "Building Spring Boot server..."
./mvnw verify || { echo "Error: failed to build Spring Boot server"; exit 1; }

# Run Spring Boot server
echo "Starting Spring Boot server..."
cd server/target || { echo "Error: server/target directory not found"; exit 1; }
java -jar benchscape-server.jar &
SERVER_PID=$!
cd ../..

# Install and start frontend
echo "Installing and starting frontend..."
cd frontend || { echo "Error: frontend directory not found"; exit 1; }
npm install || { echo "Error: failed to install npm dependencies"; exit 1; }
npm start &

# Cleanup function to stop server on script exit
cleanup() {
    echo "Stopping servers..."
    kill "$SERVER_PID" >/dev/null 2>&1
    exit 1
}

# Trap signals to cleanup properly
trap cleanup EXIT INT TERM

# Wait for processes to finish
wait
