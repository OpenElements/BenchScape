import React from "react";
import { useNavigate } from "react-router-dom";

const BenchmarkDetails = () => {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col h-full">
      <div className="p-8 flex-grow flex flex-col gap-6">
        <div>
          <h1 className="h4">BenchScape V1.1</h1>
          <h2 className="text-gray-900">
            Lorem ipsum dolor sit amet consectetur adipisicing elit.
          </h2>
        </div>
        <div className="flex flex-col gap-3 ml-2">
          <div className="flex flex-col gap-1" style={{ maxWidth: "400px" }}>
            <label className="text-xs text-gray-800">Benchmark name</label>
            <input
              className="border-2 border-slate-300 rounded"
              type="text"
              name="infrastructure"
            />
          </div>
          <div className="flex flex-col gap-1 max-w-screen-sm">
            <label className="text-xs text-gray-800">
              Benchmark description
            </label>
            <textarea
              className="border-2 border-slate-300 rounded"
              type="text"
              name="infrastructure-desc"
            />
          </div>
          {/* Table with one column and two rows */}
          <div className="mt-4">
            <p className="text-sm leading-5 font-inter mb-2 text-left font-light">
              Supported environments
            </p>

            <div class="relative overflow-x-auto">
              <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <tbody>
                  <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                    <th
                      scope="row"
                      class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                    >
                      Apple MacBook Pro 17"
                    </th>
                    <td class="px-6 py-5">DEFAULT</td>
                    <td class="px-6 py-4 text-indigo-600">Remove</td>
                  </tr>
                  <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                    <th
                      scope="row"
                      class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                    >
                      Microsoft Surface Pro
                    </th>
                    <td class="px-6 py-4 text-indigo-600">Set Default</td>
                    <td class="px-6 py-4 text-indigo-600">Remove</td>
                  </tr>
                  <tr class="bg-white dark:bg-gray-800">
                    <th
                      scope="row"
                      class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                    >
                      Mac M1
                    </th>
                    <td class="px-6 py-4 text-indigo-600">Set Default</td>
                    <td class="px-6 py-4 text-indigo-600">Remove</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div style={{ maxWidth: "400px" }}>
          <div className="flex flex-col gap-5 mt-4">
            {/* Measurement details */}
          </div>
        </div>
      </div>
      <div className="flex-grow"></div>
      <div className="flex self-end gap-2">
        <button
          className="border-2 text-sm rounded-md text-gray-800"
          style={{
            padding: "6px 18px",
          }}
          onClick={() => navigate(-1)}
        >
          Cancel
        </button>
        <button
          className="border-2 text-sm rounded-md bg-indigo-700 border-indigo-700 text-white"
          style={{
            padding: "6px 30px",
          }}
        >
          Save
        </button>
      </div>
    </div>
  );
};

export default BenchmarkDetails;
