import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useBenchMarks, useMeasurements } from "../hooks";
import Pagination from "../components/Pagination";

const BenchmarksPage = () => {
  const { data, isLoading } = useBenchMarks();

  // Number of items per page
  const [itemsPerPage, setItemsPerPage] = useState(10);

  // State for current page
  const [currentPage, setCurrentPage] = useState(1);

  // Calculate the index range for the current page
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentBenchmarks = data?.slice(indexOfFirstItem, indexOfLastItem);

  // Calculate the total number of pages
  const totalPages = Math.ceil(data?.length / itemsPerPage);

  return (
    <div className="App">
      {isLoading ? (
        "Loading..."
      ) : (
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
                          className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                        >
                          Name
                        </th>
                        <th
                          scope="col"
                          className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                        >
                          Measurement
                        </th>
                        <th
                          scope="col"
                          className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-right"
                        >
                          Graph
                        </th>
                        <th
                          scope="col"
                          className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-right"
                        >
                          Table
                        </th>
                      </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-200 bg-white">
                      {currentBenchmarks?.map((benchmark) => (
                        <tr
                          key={benchmark.id}
                          className="group hover:bg-azure transition-colors ease-in-out duration-150"
                        >
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                            {benchmark.name ?? "--"}
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                            <MeasurementLength benchmarkId={benchmark.id} />
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                            <Link
                              to={`/graph/${benchmark.id}`}
                              state={{
                                showSwitchers: true,
                                uuid: benchmark.id,
                              }}
                              className="text-primary-green text-xs font-medium transition-colors ease-in-out duration-150"
                            >
                              View Graph
                            </Link>
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                            <Link
                              to={`/table/${benchmark.id}`}
                              state={{
                                showSwitchers: true,
                                uuid: benchmark.id,
                              }}
                              className="text-primary-green text-xs font-medium transition-colors ease-in-out duration-150"
                            >
                              View Table
                            </Link>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Pagination */}
      {data?.length > 0 && (
        <Pagination
          itemsPerPage={itemsPerPage}
          setCurrentPage={setCurrentPage}
          setItemsPerPage={setItemsPerPage}
          totalPages={totalPages}
          currentPage={currentPage}
          indexOfLastItem={indexOfLastItem}
          data={data}
        />
      )}
    </div>
  );
};

export default BenchmarksPage;

function MeasurementLength({ benchmarkId }) {
  const { data } = useMeasurements(benchmarkId);
  return <>{data?.length}</>;
}
