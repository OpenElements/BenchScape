import React from "react";
import { useMeasurements } from "../../hooks/hooks";
import { useParams } from "react-router-dom";
import { tableCellStyle, tableHeaderStyle } from "./measurementsStyles";

const MeasurementsTableComponent = ({ type }) => {
  const { id } = useParams();
  const { data } = useMeasurements(id);

  console.log(data, "getting data");

  return (
    <React.Fragment>
      {/* TODO: Return a loading state once the data is still loading */}
      {data && (
        <div>
          <h2>Time Series Data</h2>
          <table style={{ borderCollapse: "collapse", width: "100%" }}>
            <thead>
              <tr>
                <th style={tableHeaderStyle}>ID</th>
                <th style={tableHeaderStyle}>Timestamp</th>
                <th style={tableHeaderStyle}>Value (ops/ms)</th>{" "}
                {/* Added unit */}
                <th style={tableHeaderStyle}>Error (ops/ms)</th>{" "}
                {/* Added unit */}
                <th style={tableHeaderStyle}>Min (ops/ms)</th>{" "}
                {/* Added unit */}
                <th style={tableHeaderStyle}>Max (ops/ms)</th>{" "}
                {/* Added unit */}
                <th style={tableHeaderStyle}>Available Processors</th>
                <th style={tableHeaderStyle}>Memory</th>
                <th style={tableHeaderStyle}>JVM Version</th>
              </tr>
            </thead>
            <tbody>
              {Object.keys(data).map((key) => {
                const item = data[key];
                return (
                  <tr key={item.id}>
                    <td style={tableCellStyle}>{item.id}</td>
                    <td style={tableCellStyle}>
                      {new Date(item.timestamp).toLocaleString()}
                    </td>{" "}
                    <td style={tableCellStyle}>{item.value.toFixed(4)}</td>{" "}
                    <td style={tableCellStyle}>{item.error.toFixed(4)}</td>{" "}
                    <td style={tableCellStyle}>{item.min.toFixed(4)}</td>{" "}
                    <td style={tableCellStyle}>{item.max.toFixed(4)}</td>{" "}
                    <td style={tableCellStyle}>{item.availableProcessors}</td>
                    <td style={tableCellStyle}>{item.memory}</td>
                    <td style={tableCellStyle}>{item.jvmVersion}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </React.Fragment>
  );
};

export default MeasurementsTableComponent;
