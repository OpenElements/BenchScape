import React from "react";
import Select from "../components/tags/select"

const OSList = [
  { id: 1, value: '11', name: 'Windows 11' },
  { id: 2, value: '10', name: 'Windows 10' },
  { id: 3, value: '7', name: 'Windows 7' },
  { id: 4, value: 'mac', name: 'Mac OS' },
]

function EnvironmentsPage() {
  return <div>
    <div className="flex items-center bg-alice-blue 2xl:px-8 2xl:py-7 px-5 py-4 w-full">
      <div className="flex flex-wrap gap-x-4 gap-y-6">
        <Select label="OS" />
        <Select label="OS Version" />
        <Select label="Architecture" />
        <Select label="CPU Cores" />
        <Select label="Memory" />
        <Select label="JVM Name" />
        <Select label="JVM Version" />
        <Select label="JMH Version" />
        <Select label="Thread Count" />
        <Select label="Fork Count" />
        <Select label="Warmup Interactions" />
        <Select label="Warmup Time" />
        <Select label="Measurement Interactions" />
        <Select label="Measurement Time" />
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
                    <th scope="col" className="py-3.5 px-4 text-left text-sm font-medium text-gray-500 uppercase">
                      Name
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase">
                      Operating Sysytem
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase">
                      JAVA
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left">
                      JMH
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left">
                      Threads
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left">
                      Forks
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left">
                      Warmap
                    </th>
                    <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-left">
                      Measurement
                    </th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200 bg-white">
                  <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                      Test Enviroment
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      Ubuntu 24.0.5
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      Temurin 11.0.7
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      11.0.7
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      6
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4 X 8 sec
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4 X 8 sec
                    </td>
                  </tr>
                  <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                      Test Enviroment
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      Ubuntu 24.0.5
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      Temurin 11.0.7
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      11.0.7
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      6
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4 X 8 sec
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4 X 8 sec
                    </td>
                  </tr>
                  <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                      Test Enviroment
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      Ubuntu 24.0.5
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      Temurin 11.0.7
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      11.0.7
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      6
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4 X 8 sec
                    </td>
                    <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                      4 X 8 sec
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
}

export default EnvironmentsPage;
