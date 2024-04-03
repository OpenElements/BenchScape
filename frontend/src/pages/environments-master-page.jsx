import React, { useState, useEffect } from "react";
import Select from "../components/Select";
import {
  useEnvironmentMetadata,
  useEnvironments,
  useForOsFamilyFilter,
} from "../hooks";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faApple,
  faWindows,
  faLinux,
} from "@fortawesome/free-brands-svg-icons";
import { faMemory, faMicrochip } from "@fortawesome/free-solid-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import Pagination from "../components/Pagination";
import { OverflowMenu } from "../components";
import { deleteEnvironment } from "../api";
import { apiUrl } from "../utils/constants";
import { createAppBarConfig } from "../utils";
import { useSWRConfig } from "swr";
import { exportEnvironmentsCsv } from "../api";

function EnvironmentsPage() {
  const navigate = useNavigate();
  const { mutate: globalMutator } = useSWRConfig();
  const [filters, setFilters] = useState({
    osFamily: "",
    osVersion: "",
    systemArch: "",
    cores: "",
    memory: "",
    jvmName: "",
    jvmVersion: "",
    jmhVersion: "",
  });
  const [hoveredRow, setHoveredRow] = useState(null);

  const handleRowHover = (rowIndex) => {
    setHoveredRow(rowIndex);
  };

  const { data: environments, mutate } = useEnvironments(filters);
  const { data: forOsFamilyOptionsFiltered } = useForOsFamilyFilter(
    filters.osFamily,
  );
  const { data: osFamilyOptions } = useEnvironmentMetadata("osFamily");
  const { data: archOptions } = useEnvironmentMetadata("arch");
  const { data: jvmVersionOptions } = useEnvironmentMetadata("jvmVersion");
  const { data: coresOptions } = useEnvironmentMetadata("cores");
  const { data: jvmNameOptions } = useEnvironmentMetadata("jvmName");
  const { data: jvhVersionOptions } = useEnvironmentMetadata("jmhVersion");
  const { data: systemMemoryReadableOptions } = useEnvironmentMetadata(
    "systemMemoryReadable",
  );

  const [itemsPerPage, setItemsPerPage] = useState(8);
  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentEnvironments = environments?.slice(
    indexOfFirstItem,
    indexOfLastItem,
  );

  const totalPages = Math.ceil(environments?.length / itemsPerPage);

  const handleSelectionChange = (name, { target }) => {
    setFilters((prev) => ({ ...prev, [name]: target.value }));
  };

  const handleExportCsv = async () => {
    await exportEnvironmentsCsv();
  };

  useEffect(() => {
    createAppBarConfig({
      title: "Environments",
      actions: [
        {
          name: "Export CSV",
          action: handleExportCsv,
        },
      ],
    });
  }, []);

  return (
    <div>
      <div className="flex items-center bg-alice-blue 2xl:px-8 2xl:py-7 px-5 py-4 w-full">
        <div className="flex flex-wrap gap-x-4 gap-y-6">
          <Select
            label="OS Family"
            options={osFamilyOptions?.filter((option) => option !== "UNKNOWN")}
            value={filters?.osFamily !== "UNKNOWN" ? filters.osFamily : null}
            valueExtractor={(name) => name}
            labelExtractor={(name) =>
              name === "MAC_OS"
                ? "Mac OS"
                : name.charAt(0).toUpperCase() + name.slice(1).toLowerCase()
            }
            onChange={(e) =>
              handleSelectionChange("osFamily", e !== "UNKNOWN" ? e : null)
            }
          />

          <Select
            label="OS Version"
            options={forOsFamilyOptionsFiltered}
            value={filters["forOsFamily"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("forOsFamily", e)}
          />
          <Select
            label="Architecture"
            options={archOptions}
            value={filters["architecture"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("systemArch", e)}
          />
          <Select
            label="CPU Cores"
            options={coresOptions}
            value={filters["cores"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("cores", e)}
          />
          <Select
            label="Memory"
            options={systemMemoryReadableOptions}
            value={filters["memory"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("memory", e)}
          />
          <Select
            label="JVM Name"
            options={jvmNameOptions}
            value={filters["jvmName"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("jvmName", e)}
          />
          <Select
            disabled={filters.osFamily === ""}
            label="JVM Version"
            options={jvmVersionOptions}
            value={filters["jvmVersion"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("jvmVersion", e)}
          />
          <Select
            disabled={filters.osFamily === ""}
            label="JMH Version"
            options={jvhVersionOptions}
            value={filters["jmhVersion"]}
            valueExtractor={(name) => name}
            labelExtractor={(name) => name}
            onChange={(e) => handleSelectionChange("jmhVersion", e)}
          />
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
                    {currentEnvironments?.map((environment, index) => (
                      <tr
                        className="group hover:bg-azure transition-colors ease-in-out duration-150"
                        key={index}
                        onMouseEnter={() => handleRowHover(index)}
                        onMouseLeave={() => handleRowHover(null)}
                      >
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                          <Link to={`/environment/${environment.id}`}>
                            {environment.name ?? "--"}
                          </Link>
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {(environment.osFamily === "MAC_OS" && (
                            <FontAwesomeIcon icon={faApple} className="mr-2" />
                          )) ||
                            (environment.osFamily === "WINDOWS" && (
                              <FontAwesomeIcon
                                icon={faWindows}
                                className="mr-2"
                              />
                            )) ||
                            (environment.osFamily === "LINUX" && (
                              <FontAwesomeIcon
                                icon={faLinux}
                                className="mr-2"
                              />
                            ))}
                          {environment.osFamily &&
                          environment.osFamily !== "UNKNOWN"
                            ? environment.osFamily === "MAC_OS"
                              ? "Mac OS "
                              : `${environment.osFamily
                                  .charAt(0)
                                  .toUpperCase()}${environment.osFamily
                                  .slice(1)
                                  .toLowerCase()} `
                            : "-"}
                          {environment.osVersion || ""}
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
                            <>
                              {environment.systemMemoryReadable
                                ? `${environment.systemMemoryReadable}`
                                : "-"}
                            </>
                          )}
                        </td>

                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.jvmName || environment.jvmVersion
                            ? `${environment.jvmName ?? ""} ${
                                environment.jvmVersion ?? ""
                              }`
                            : "-"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.jmhVersion ?? "-"}
                        </td>
                        <div className="absolute right-16 mt-3">
                          <OverflowMenu
                            showMenu={index === hoveredRow}
                            menuItems={[
                              {
                                name: "Edit",
                                action: () =>
                                  navigate(`/environment/${environment.id}`),
                              },
                              {
                                delete: true,
                                name: "Delete",
                                action: async () => {
                                  await deleteEnvironment(environment.id).then(
                                    () => {
                                      mutate();

                                      // updates the count value in the Side nav
                                      globalMutator(
                                        `${apiUrl}/api/v2/environment/count`,
                                      );
                                    },
                                  );
                                },
                              },
                            ]}
                          />
                        </div>
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
      {environments?.length > 0 && (
        <Pagination
          itemsPerPage={itemsPerPage}
          setCurrentPage={setCurrentPage}
          setItemsPerPage={setItemsPerPage}
          totalPages={totalPages}
          currentPage={currentPage}
          indexOfLastItem={indexOfLastItem}
          data={environments}
        />
      )}
    </div>
  );
}

export default EnvironmentsPage;
