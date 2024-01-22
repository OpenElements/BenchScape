import React, { useState } from "react";
import Select from "../components/tags/select";
import { useEnvironmentMetadata, useEnvironments } from "../hooks";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faApple,
  faWindows,
  faLinux,
} from "@fortawesome/free-brands-svg-icons";
import { faMemory, faMicrochip } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";

function EnvironmentsPage() {
  const { data: environments } = useEnvironments();
  const { data: osOptions } = useEnvironmentMetadata("os");
  const { data: osVersionOptions } = useEnvironmentMetadata("osVersion");
  const { data: archOptions } = useEnvironmentMetadata("arch");
  const { data: memoryOptions } = useEnvironmentMetadata("memory");
  const { data: jvmVersionOptions } = useEnvironmentMetadata("jvmVersion");
  const { data: coresOptions } = useEnvironmentMetadata("cores");
  const { data: jvmNameOptions } = useEnvironmentMetadata("jvmName");
  const { data: jvhVersionOptions } = useEnvironmentMetadata("jmhVersion");
  const { data: systemMemoryReadableOptions } = useEnvironmentMetadata(
    "systemMemoryReadable"
  );
  const { data: osFamilyOptions } = useEnvironmentMetadata("osFamily");

  // Number of items per page
  const [itemsPerPage, setItemsPerPage] = useState(8);

  // State for current page
  const [currentPage, setCurrentPage] = useState(1);

  // Calculate the index range for the current page
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentEnvironments = environments?.slice(
    indexOfFirstItem,
    indexOfLastItem
  );

  // Calculate the total number of pages
  const totalPages = Math.ceil(environments?.length / itemsPerPage);

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
    <div>
      <div className="flex items-center bg-alice-blue 2xl:px-8 2xl:py-7 px-5 py-4 w-full">
        <div className="flex flex-wrap gap-x-4 gap-y-6">
          <Select label="OS" options={osOptions} />
          <Select label="OS Version" options={osVersionOptions} />
          <Select label="Architecture" options={archOptions} />
          <Select label="CPU Cores" options={coresOptions} />
          <Select label="Memory" options={memoryOptions} />
          <Select label="JVM Name" options={jvmNameOptions} />
          <Select label="JVM Version" options={jvmVersionOptions} />
          <Select label="JMH Version" options={jvhVersionOptions} />
          <Select
            label="Memory Readable"
            options={systemMemoryReadableOptions}
          />
          <Select label="OS Family" options={osFamilyOptions} />
        </div>
      </div>

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
                        Name
                      </th>
                      <th
                        scope="col"
                        className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                      >
                        Operating System
                      </th>
                      <th
                        scope="col"
                        className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                      >
                        Hardware
                      </th>
                      <th
                        scope="col"
                        className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase"
                      >
                        JAVA
                      </th>
                      <th
                        scope="col"
                        className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left"
                      >
                        JMH
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 bg-white">
                    {currentEnvironments?.map((environment) => (
                      <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                          <Link to={`/environments/${environment.id}`}>
                            {environment.name ?? "--"}
                          </Link>
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.osName === "Mac OS X" && (
                            <FontAwesomeIcon icon={faApple} className="mr-2" />
                          )}
                          {environment.osName === "Windows" && (
                            <FontAwesomeIcon
                              icon={faWindows}
                              className="mr-2"
                            />
                          )}
                          {environment.osName === "Linux" && (
                            <FontAwesomeIcon icon={faLinux} className="mr-2" />
                          )}
                          {`${environment.osName ?? "-"} ${
                            environment.osVersion ?? " "
                          }`}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.systemArch &&
                          environment.systemProcessors ? (
                            <>
                              {`${environment.systemArch}`}
                              <FontAwesomeIcon
                                icon={faMicrochip}
                                className="ml-2 & mr-2"
                                style={{}}
                              />
                              {`${environment.systemProcessors}`}
                              <FontAwesomeIcon
                                icon={faMemory}
                                className="ml-2 & mr-2"
                                style={{}}
                              />
                            </>
                          ) : (
                            "-"
                          )}
                          {`${environment.systemMemory ?? " "}`}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {`${environment.jvmName ?? "-"} ${
                            environment.jvmVersion ?? " "
                          }`}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.jmhVersion ?? "-"}
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
            disabled={indexOfLastItem >= environments?.length}
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
            <option value={8}>8</option>
            <option value={9}>9</option>
            <option value={10}>10</option>
            <option value={11}>11</option>
          </select>
        </div>
      </div>
    </div>
  );
}

export default EnvironmentsPage;
