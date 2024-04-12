import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { createAppBarConfig, itemsPerPage } from "../../utils";
import { useBenchMarks, useMeasurements } from "../../hooks";
import { Pagination } from "../../components";
import { exportBenchmarksCsv } from "../../api";

const BenchmarksPage = () => {
  const { data, isLoading } = useBenchMarks();

  const [currentPage, setCurrentPage] = useState(1);
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentBenchmarks = data?.slice(indexOfFirstItem, indexOfLastItem);

  const totalPages = Math.ceil(data?.length / itemsPerPage);

  const handleExportCsv = async () => {
    await exportBenchmarksCsv();
  };

  useEffect(() => {
    createAppBarConfig({
      title: "Benchmarks",
      actions: [
        {
          name: "Export CSV",
          action: handleExportCsv,
        },
      ],
    });
  }, []);

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
                              to={`/benchmark/graph/${benchmark.id}`}
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
                              to={`/benchmark/table/${benchmark.id}`}
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
          kind="cardbox"
          setCurrentPage={setCurrentPage}
          totalPages={totalPages}
          currentPage={currentPage}
        />
      )}
    </div>
  );
};

export default BenchmarksPage;

function MeasurementLength({ benchmarkId }) {
  const { data } = useMeasurements({ benchmarkId: benchmarkId });
  return <>{data?.length}</>;
}
