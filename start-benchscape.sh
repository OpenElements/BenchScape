#!/bin/bash

# Start Spring Boot server
echo "Starting Spring Boot server..."
cd server/target || { echo "Error: server/target directory not found"; exit 1; }
java -jar benchscape-server.jar &
SERVER_PID=$!
cd ../..

# Start frontend server
echo "Starting frontend server..."
cd frontend || { echo "Error: frontend directory not found"; exit 1; }
npm start &

# Cleanup function to stop servers on script exit
cleanup() {
    echo "Stopping servers..."
    kill "$SERVER_PID" >/dev/null 2>&1
    exit 1
}

# Trap signals to cleanup properly
trap cleanup EXIT INT TERM

# Wait for processes to finish
wait
