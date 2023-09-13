import React, {useEffect, useState} from "react";
import "./App.css";

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
          <h2>BenchScape V1.1</h2>
          {isLoading ? (
              "Loading..."
          ) : (
              <table>
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Unit</th>
                </tr>
                </thead>
                <tbody>
                {data.map((benchmark) => (
                    <tr key={benchmark.id}>
                      <td>{benchmark.id}</td>
                      <td>{benchmark.name}</td>
                      <td>{benchmark.unit}</td>
                    </tr>
                ))}
                </tbody>
              </table>
          )}
        </header>
      </div>
  );
}

export default App;
