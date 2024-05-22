import { useState, useEffect } from "react";
import { useMeasurements, useEnvironments, useFilters, useBranches } from "../../hooks";
import { useParams, useNavigate } from "react-router-dom";
import {
  createAppBarConfig,
  itemsPerPage,
  setGlobalFilters,
} from "../../utils";
import { dataSlicer } from "../../utils";
import { exportMeasurementsCsv } from "../../api";
import { Select, Datepicker, Pagination } from "../../components";

const MeasurementsTableComponent = () => {
  const { id } = useParams();
  const { filters: initialFilters } = useFilters();
  const navigate = useNavigate();

  const [filters, setFilters] = useState({
    benchmarkId: id,
    ...initialFilters,
  });

  const { data: branches } = useBranches(id);

  const { data: environments } = useEnvironments();
  const { data } = useMeasurements(filters);
  const [currentPage, setCurrentPage] = useState(1);
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = data?.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = dataSlicer(data, itemsPerPage);
  const handDateChange = (field: string, date: string) => {
    setFilters((prev) => ({ ...prev, [field]: date }));
  };

  const handleEnvironmentChange = (e) => {
    const selectedEnvironmentId = e.target.value;
    setFilters((prev) => ({
      ...prev,
      environmentIds: selectedEnvironmentId,
    }));
  };

  const handleExportCsv = async () => {
    await exportMeasurementsCsv(id);
  };

  const handleBranchSelectChange = (field: string, values: Array<string>) => {
    setFilters((prev) => ({ ...prev, [field]: values }));
  };

  useEffect(() => {
    createAppBarConfig({
      title: "Benchmark details",
      actions: [
        {
          name: "Graph View",
          action: () => navigate(`/benchmark/graph/${id}`),
        },
        {
          name: "Export CSV",
          action: handleExportCsv,
        },
      ],
    });
    // eslint-disable-next-line
  }, [filters.environmentIds]);

  useEffect(() => {
    setGlobalFilters(filters);
  }, [filters]);

  return (
    <div className="py-6">
      <div className="flow-root">
        <div className="ml-8 flex gap-2">
          <Select
            label="Environment"
            options={environments}
            value={filters.environmentIds}
            valueExtractor={(option) => option.id}
            labelExtractor={(option) => option.name}
            onChange={handleEnvironmentChange}
          />

        <div className="flex gap-2">
          <Select
            label="Branch"
            options={branches}
            value={filters.benchmarkId}
            valueExtractor={(option) => option}
            labelExtractor={(option) => option}
            onChange={(e) => handleBranchSelectChange("benchmarkId", e.target.value)}
          />
        </div>
          <Datepicker
            label="Start Date"
            placeholder="Enter start date"
            value={filters.start}
            onChange={(date) => handDateChange("start", date)}
          />
          <Datepicker
            label="End Date"
            placeholder="Enter end date"
            value={filters.end}
            onChange={(date) => handDateChange("end", date)}
          />
        </div>
        <div className="mt-4 overflow-x-auto">
          <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <div className="overflow-hidden ring-1 ring-black ring-opacity-5 sm:rounded-sm">
              <table className="min-w-full divide-y divide-gray-300">
                <thead className="bg-gray-50">
                  <tr>
                    <th
                      scope="col"
                      className="px-4 py-3.5 text-left text-sm font-semibold uppercase text-gray-500"
                    >
                      Timestamp
                    </th>
                    <th
                      scope="col"
                      className="px-4 py-3.5 text-left text-sm font-semibold uppercase text-gray-500"
                    >
                      Value (ops/ms)
                    </th>
                    <th
                      scope="col"
                      className="px-4 py-3.5 text-left text-sm font-semibold uppercase text-gray-500"
                    >
                      Error (ops/ms)
                    </th>
                    <th
                      scope="col"
                      className="px-4 py-3.5 text-left text-sm font-semibold uppercase text-gray-500"
                    >
                      Min (ops/ms)
                    </th>
                    <th
                      scope="col"
                      className="px-4 py-3.5 text-left text-sm font-semibold uppercase text-gray-500"
                    >
                      Max (ops/ms)
                    </th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200 bg-white">
                  {currentItems?.map(
                    ({ id, timestamp, error, min, max, value }) => (
                      <tr
                        key={id}
                        className="group transition-colors duration-150 ease-in-out hover:bg-azure"
                      >
                        <td className="whitespace-nowrap px-4 py-3.5 text-sm font-light text-gray-500">
                          {timestamp
                            ? new Date(timestamp).toLocaleString()
                            : "--"}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3.5 text-sm font-light text-gray-500">
                          {value ? value.toFixed(4) : "--"}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3.5 text-sm font-light text-gray-500">
                          {error ? error.toFixed(4) : "--"}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3.5 text-sm font-light text-gray-500">
                          {min ? min.toFixed(4) : "--"}
                        </td>
                        <td className="whitespace-nowrap px-4 py-3.5 text-sm font-light text-gray-500">
                          {max ? max.toFixed(4) : "--"}
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
      {/* Pagination */}
      <Pagination
        kind="cardbox"
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        currentPage={currentPage}
      />
    </div>
  );
};

export default MeasurementsTableComponent;
