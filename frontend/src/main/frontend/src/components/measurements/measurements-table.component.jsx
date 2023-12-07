import React from "react";
import { useMeasurements } from "../../hooks/hooks";
import { useParams } from "react-router-dom";

const MeasurementsTableComponent = ({ type }) => {
  const { id } = useParams();
  const { data } = useMeasurements(id);

  console.log(data, "getting data");

  // return (
  //   <React.Fragment>
  //     {/* TODO: Return a loading state once the data is still loading */}
  //     {data && (
  //       <div>
  //         <h2>Time Series Data</h2>
  //         <table style={{ borderCollapse: "collapse", width: "100%" }}>
  //           <thead>
  //             <tr>
  //               <th style={tableHeaderStyle}>ID</th>
  //               <th style={tableHeaderStyle}>Timestamp</th>
  //               <th style={tableHeaderStyle}>Value (ops/ms)</th>{" "}
  //               {/* Added unit */}
  //               <th style={tableHeaderStyle}>Error (ops/ms)</th>{" "}
  //               {/* Added unit */}
  //               <th style={tableHeaderStyle}>Min (ops/ms)</th>{" "}
  //               {/* Added unit */}
  //               <th style={tableHeaderStyle}>Max (ops/ms)</th>{" "}
  //               {/* Added unit */}
  //               <th style={tableHeaderStyle}>Available Processors</th>
  //               <th style={tableHeaderStyle}>Memory</th>
  //               <th style={tableHeaderStyle}>JVM Version</th>
  //             </tr>
  //           </thead>
  //           <tbody>
  //             {Object.keys(data).map((key) => {
  //               const item = data[key];
  //               return (
  //                 <tr key={item.id}>
  //                   <td style={tableCellStyle}>{item.id}</td>
  //                   <td style={tableCellStyle}>
  //                     {new Date(item.timestamp).toLocaleString()}
  //                   </td>{" "}
  //                   <td style={tableCellStyle}>{item.value.toFixed(4)}</td>{" "}
  //                   <td style={tableCellStyle}>{item.error.toFixed(4)}</td>{" "}
  //                   <td style={tableCellStyle}>{item.min.toFixed(4)}</td>{" "}
  //                   <td style={tableCellStyle}>{item.max.toFixed(4)}</td>{" "}
  //                   <td style={tableCellStyle}>{item.availableProcessors}</td>
  //                   <td style={tableCellStyle}>{item.memory}</td>
  //                   <td style={tableCellStyle}>{item.jvmVersion}</td>
  //                 </tr>
  //               );
  //             })}
  //           </tbody>
  //         </table>
  //       </div>
  //     )}
  //   </React.Fragment>
  // );

  return (
    <div className="py-6">
      <div className="flow-root">
        <div className="overflow-x-auto">
          <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <div className="overflow-hidden ring-1 ring-black ring-opacity-5 sm:rounded-sm">
              <table className="min-w-full divide-y divide-gray-300">
                <thead className="bg-gray-50">
                  <tr>
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-medium text-gray-500 uppercase"
                    >
                      ID
                    </th>
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      Timestamp
                    </th>
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      {"Value (ops/ms)"}
                    </th>
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left"
                    >
                      {"Error (ops/ms)"}
                    </th>
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      {"Min (ops/ms"}
                    </th>{" "}
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      {"Max (ops/ms"}
                    </th>{" "}
                    {/* <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      Available Processors
                    </th>
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      Memory
                    </th>{" "}
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      JMH Version
                    </th> */}
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200 bg-white">
                  {data?.map(
                    ({ id, timestamp, error, min, max, unit, value }) => (
                      <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                          {id ?? "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {timestamp
                            ? new Date(timestamp).toLocaleString()
                            : "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {value ? value.toFixed(4) : "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {error ? error.toFixed(4) : "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {min ? min.toFixed(4) : "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {max ? max.toFixed(4) : "--"}
                          {/* </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                        {environment.jmhVersion ?? "--"}
                      </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                        {environment.jmhVersion ?? "--"}
                      </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                        {environment.jmhVersion ?? "--"} */}
                        </td>
                      </tr>
                    )
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MeasurementsTableComponent;
