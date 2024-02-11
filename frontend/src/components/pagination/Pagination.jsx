import React from "react";

const Pagination = ({
  itemsPerPage,
  setCurrentPage,
  setItemsPerPage,
  totalPages,
  currentPage,
  indexOfLastItem,
  data,
}) => {
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
    <div className="px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
      <div className="flex-1 flex justify-between">
        {/* Previous Page Button */}
        <button
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 1}
          className="relative inline-flex items-center px-2 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
        >
          Previous
        </button>
        {/* Page numbers and page count */}
        <div className="flex items-center space-x-2">
          <span className="text-gray-500">Page</span>
          <select
            value={currentPage}
            onChange={(e) => handlePageChange(parseInt(e.target.value, 10))}
            className="relative inline-flex items-center pl-2 py-1 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
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
          className="relative inline-flex items-center px-2 py-2 mr-3 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
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
          className="relative inline-flex items-center pl-2 py-1 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring focus:border-blue-300 active:bg-gray-200"
        >
          <option value={10}>10</option>
          <option value={11}>11</option>
          <option value={12}>12</option>
          <option value={13}>13</option>
          {/* Add more options as needed */}
        </select>
      </div>
    </div>
  );
};

export default Pagination;
