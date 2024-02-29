import React, { useState } from "react";
import { useMeasurements } from "../hooks";
import { useParams } from "react-router-dom";
import Pagination from "../components/Pagination";
import { dataSlicer } from "../utils";
import Datepicker from "../components/DatePicker";
import Select from "../components/Select";

const MeasurementsTableComponent = ({ type }) => {
  const { id } = useParams();
  const { data } = useMeasurements(id);

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

  return (
    <div className="py-6">
      <div className="flow-root">
        <div className="flex gap-2 justify-around items-center mt-4">
          <Select
            label="Environment"
            options={["options"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
          />
          <div className="flex gap-4 ml-4">
            <Datepicker label="Start Date" />
            <Datepicker label="End Date" />
          </div>
          <div className="flex gap-4 mt-5">
            <div className="flex items-center">
              <input
                name="ShowMin"
                type="checkbox"
                className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
              />
              <label htmlFor="ShowMin" className="ml-2 text-xs text-gray-500">
                Show Minimum
              </label>
            </div>
            <div className="flex items-center">
              <input
                name="ShowMax"
                type="checkbox"
                className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
              />
              <label htmlFor="ShowMax" className="ml-2 text-xs text-gray-500">
                Show Maximum
              </label>
            </div>
            <div className="flex items-center">
              <input
                name="ShowError"
                type="checkbox"
                className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
              />
              <label htmlFor="ShowError" className="ml-2 text-xs text-gray-500">
                Show Error
              </label>
            </div>
          </div>
          {/* <Select
            label="Units"
            options={["options"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
          /> */}
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
