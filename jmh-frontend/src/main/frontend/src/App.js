import React, { useState, useEffect } from "react";
import { Link, Route, Routes, useParams } from "react-router-dom";
import axios from "axios";
import "./App.css";

function App() {
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const apiUrl = process.env.REACT_APP_API_URL;

  useEffect(() => {
    // Define the API URL here using the environment variable

    // Fetch data from the "/api/benchmark" endpoint
    axios
      .get(`${apiUrl}/api/benchmark`)
      // .then((response) => response.data)
      .then((response) => {
        setData(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setIsLoading(false);
      });
  }, [apiUrl]);

  console.log(data);

  return (
    <Routes>
      <Route
        path="/"
        element={
          <div className="App">
            <header className="App-header">
              <h2>Benchmark Data</h2>
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
                        <td>
                          <Link to={`/graph/${benchmark.id}`}>Graph</Link>
                          <Link to={`/table/${benchmark.id}`}>Table</Link>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
            </header>
          </div>
        }
      />
      <Route path="/graph/:id" element={<Graph />} />
      <Route path="/table/:id" element={<Table />} />
    </Routes>
  );
}

export default App;

const Graph = () => {
  const [grapDocument, setGraphDocument] = useState(null);
  const { id } = useParams();
  const env = process.env.REACT_APP_API_URL;
  const url = `${env}/timeseries/graph?id=${id}`;

  useEffect(() => {
    axios
      .get(url)
      .then((res) => setGraphDocument(res.data))
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, [url]);

  return (
    <React.Fragment>
      {/****  haven't tried  this out though ***/}
      <div dangerouslySetInnerHTML={{ __html: grapDocument }}></div>
    </React.Fragment>
  );
};

const Table = () => {
  const [tableDocument, setTableDocument] = useState(null);
  const { id } = useParams();
  const env = process.env.REACT_APP_API_URL;
  const tableUrl = `${env}/timeseries/table?id=${id}`;

  useEffect(() => {
    axios
      .get(tableUrl)
      .then((res) => setTableDocument(res.data))
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, [tableUrl]);

  return (
    <React.Fragment>
      {/****  haven't tried  this out though ***/}
      <div dangerouslySetInnerHTML={{ __html: tableDocument }}></div>
    </React.Fragment>
  );
};

async function getData(url) {
  return await axios
    .get(url)
    .then((data) => data.data)
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}
