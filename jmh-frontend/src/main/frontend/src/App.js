import React, { useState, useEffect } from "react";

function App() {
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Define the API URL here using the environment variable
    const apiUrl = process.env.REACT_APP_API_URL;

    // Fetch data from the "/api/benchmark" endpoint
    fetch(`${apiUrl}/api/benchmark`)
      .then((response) => response.json())
      .then((data) => {
        setData(data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setIsLoading(false);
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <p>
          {isLoading
            ? "Loading..."
            : // Display the fetched data
              data.map((benchmark) => (
                <div key={benchmark.id}>
                  <h2>{benchmark.name}</h2>
                  {/* Display other properties of the benchmark */}
                </div>
              ))}
        </p>
      </header>
    </div>
  );
}

export default App;
