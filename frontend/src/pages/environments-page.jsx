import React from "react";
import Select from "../components/tags/select";
import { useEnvironmentMetadata, useEnvironments } from "../hooks";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faApple,
  faWindows,
  faLinux,
} from "@fortawesome/free-brands-svg-icons";
import { faMemory, faMicrochip } from "@fortawesome/free-solid-svg-icons";

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
                    {environments?.map((environment) => (
                      <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                          {environment.name ?? "--"}
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
    </div>
  );
}

export default EnvironmentsPage;
