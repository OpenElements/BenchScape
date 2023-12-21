import React, { useState } from "react";
import { useMeasurements } from "../../hooks";
import { useParams } from "react-router-dom";

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
  const totalPages = Math.ceil(data?.length / itemsPerPage);

  // Function to handle page change
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // Function to handle items per page change
  const handleItemsPerPageChange = (event) => {
    const newItemsPerPage = parseInt(event.target.value, 10);
    setCurrentPage(1);
    setItemsPerPage(newItemsPerPage);
  };

  // Array of page numbers
  const pageNumbers = Array.from(
    { length: totalPages },
    (_, index) => index + 1
  );

  return (
    <div className="py-6">
      <div className="flow-root">
        <div className="overflow-x-auto">
          <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <div className="overflow-hidden ring-1 ring-black ring-opacity-5 sm:rounded-sm">
              <table className="min-w-full divide-y divide-gray-300">
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
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 bg-white">
                    {currentItems?.map(
                      ({ id, timestamp, error, min, max, unit, value }) => (
                        <tr
                          key={id} // Add a unique key for each row
                          className="group hover:bg-azure transition-colors ease-in-out duration-150"
                        >
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
                          </td>
                        </tr>
                      )
                    )}
                  </tbody>
                </table>
              </table>
            </div>
          </div>
        </div>
      </div>
      {/* Pagination */}
      <div className="px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
        <div className="flex-1 flex justify-between">
          {/* Previous Page Button */}
          <button
            onClick={() => handlePageChange(currentPage - 1)}
            disabled={currentPage === 1}
            className="relative inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
          >
            Previous
          </button>
          {/* Page numbers and page count */}
          <div className="flex items-center space-x-2">
            <span className="text-gray-500">Page</span>
            <select
              value={currentPage}
              onChange={(e) => handlePageChange(parseInt(e.target.value, 10))}
              className="relative inline-flex items-center px-2 py-1 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
            >
              {pageNumbers.map((pageNumber) => (
                <option key={pageNumber} value={pageNumber}>
                  {pageNumber}
                </option>
              ))}
            </select>
            <span className="text-gray-500">of {totalPages}</span>
          </div>
          {/* Next Page Button */}
          <button
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={indexOfLastItem >= data?.length}
            className="relative inline-flex items-center px-4 py-2 ml-3 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
          >
            Next
          </button>
        </div>
        {/* Items per page dropdown */}
        <div className="flex items-center space-x-2">
          <span className="text-gray-500">Items per page:</span>
          <select
            value={itemsPerPage}
            onChange={handleItemsPerPageChange}
            className="relative inline-flex items-center px-2 py-1 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
          >
            <option value={10}>10</option>
            <option value={11}>11</option>
            <option value={12}>12</option>
            <option value={13}>13</option>
            {/* Add more options as needed */}
          </select>
        </div>
      </div>
    </div>
  );
};

export default MeasurementsTableComponent;
