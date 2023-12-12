import React from "react";
import Select from "../components/tags/select";
import { useEnvironmentMetadata, useEnvironments } from "../hooks";

// const OSList = [
//   { id: 1, value: '11', name: 'Windows 11' },
//   { id: 2, value: '10', name: 'Windows 10' },
//   { id: 3, value: '7', name: 'Windows 7' },
//   { id: 4, value: 'mac', name: 'Mac OS' },
// ]

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
  // const { data: osOptions } = useEnvironmentMetadata('os');
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
          {/* <Select label="Thread Count" /> */}
          {/* <Select label="Fork Count" />
          <Select label="Warmup Interactions" />
          <Select label="Warmup Time" />
          <Select label="Measurement Interactions" />
          <Select label="Measurement Time" /> */}
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
                      {/* <th
                        scope="col"
                        className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left"
                      >
                        Forks
                      </th>
                      <th
                        scope="col"
                        className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left"
                      >
                        Warmap
                      </th>
                      <th
                        scope="col"
                        className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left"
                      >
                        Measurement
                      </th> */}
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 bg-white">
                    {environments?.map((environment) => (
                      <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                          {environment.name ?? "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.osName ?? "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {"--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.jvmVersion ?? "--"}
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          {environment.jmhVersion ?? "--"}
                        </td>
                        {/* <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          4
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          4 X 8 sec
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          4 X 8 sec
                        </td> */}
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
