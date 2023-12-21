import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useBenchMarks } from "../hooks";

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
                          className="py-3.5 px-4 text-left text-sm font-medium text-gray-500 uppercase"
                        >
                          ID
                        </th>
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
                          Unit
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
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                            {benchmark.id}
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                            {benchmark.name}
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                            15
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                            <Link
                              to={`/graph/${benchmark.id}`}
                              className="text-primary-green text-xs font-medium transition-colors ease-in-out duration-150"
                            >
                              View Graph
                            </Link>
                          </td>
                          <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                            <Link
                              to={`/table/${benchmark.id}`}
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
              {Array.from({ length: totalPages }, (_, index) => index + 1).map(
                (pageNumber) => (
                  <option key={pageNumber} value={pageNumber}>
                    {pageNumber}
                  </option>
                )
              )}
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
            <option value={12}>12</option>
            <option value={13}>13</option>
          </select>
        </div>
      </div>
    </div>
  );
};

export default BenchmarksPage;
