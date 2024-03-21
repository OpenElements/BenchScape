import React, { useState, useEffect } from "react";
import { useMeasurements } from "../hooks";
import { useParams, useNavigate } from "react-router-dom";
import { createAppBarConfig } from "../utils";
import { dataSlicer } from "../utils";
import Pagination from "../components/Pagination";
import Datepicker from "../components/DatePicker";
import { exportMeasurementsCsv } from "../api";

const MeasurementsTableComponent = ({ type }) => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [filters, setFilters] = useState({
    benchmarkId: id,
    start: "",
    end: "",
  });

  const { data } = useMeasurements(filters);

  // Number of items per page
  const [itemsPerPage, setItemsPerPage] = useState(10);

  // State for current page
  const [currentPage, setCurrentPage] = useState(1);

  // Calculate the index range for the current page
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = data?.slice(indexOfFirstItem, indexOfLastItem);

  // Calculate the total number of pages
  const totalPages = dataSlicer(data, itemsPerPage);

  const handDateChange = (field, date) => {
    setFilters((prev) => ({ ...prev, [field]: date }));
  };

  const handleExportCsv = async () => {
    await exportMeasurementsCsv(id);
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
  }, []);

  return (
    <div className="py-6">
      <div className="flow-root">
        <div className="flex gap-2 ml-8">
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
        <div className="overflow-x-auto mt-4">
          {" "}
          {/* Added margin top for space */}
          <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <div className="overflow-hidden ring-1 ring-black ring-opacity-5 sm:rounded-sm">
              <table className="min-w-full divide-y divide-gray-300">
                <thead className="bg-gray-50">
                  <tr>
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
                      {"Min (ops/ms)"}
                    </th>{" "}
                    <th
                      scope="col"
                      className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                    >
                      {"Max (ops/ms)"}
                    </th>{" "}
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200 bg-white">
                  {currentItems?.map(
                    ({ id, timestamp, error, min, max, unit, value }) => (
                      <tr
                        key={id}
                        className="group hover:bg-azure transition-colors ease-in-out duration-150"
                      >
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
        itemsPerPage={itemsPerPage}
        setCurrentPage={setCurrentPage}
        setItemsPerPage={setItemsPerPage}
        totalPages={totalPages}
        currentPage={currentPage}
        indexOfLastItem={indexOfLastItem}
        data={data}
      />
    </div>
  );
};

export default MeasurementsTableComponent;
