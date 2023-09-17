import { Link } from "react-router-dom";
import { useBenchMarks } from "../hooks/hooks";

const HomePage = () => {
  const { data, isLoading } = useBenchMarks();

  return (
    <main class="w-full h-full px-4 sm:px-6 xl:px-7 xl:pb-5">
      <div className="bg-white rounded-lg w-full h-full shadow-lg">
        <div className="App">
          {isLoading ? (
            "Loading..."
          ) : (
            <div class="px-4 sm:px-6 lg:px-8 py-7">
              <div class="sm:flex sm:items-center">
                <div class="sm:flex-auto">
                  <h1 class="text-xl font-semibold leading-6 text-primary-navy">
                    BenchScape V1.1
                  </h1>
                  <p class="mt-2 text-sm text-gray-700">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Phasellus
                  </p>
                </div>
                <div class="mt-4 sm:ml-16 sm:mt-0 sm:flex-none">
                  <button
                    type="button"
                    class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  >
                    Add user
                  </button>
                </div>
              </div>
              <div class="mt-8 flow-root">
                <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
                  <div class="inline-block min-w-full py-2 align-middle">
                    <table class="min-w-full divide-y divide-gray-300">
                      <thead>
                        <tr>
                          <th
                            scope="col"
                            class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-primary-navy sm:pl-6 lg:pl-8"
                          >
                            ID
                          </th>
                          <th
                            scope="col"
                            class="px-3 py-3.5 text-left text-sm font-semibold text-primary-navy"
                          >
                            Name
                          </th>
                          <th
                            scope="col"
                            class="relative py-3.5 pl-3 pr-4 sm:pr-6 lg:pr-8 text-sm font-semibold text-primary-navy"
                          >
                            Unit
                          </th>
                        </tr>
                      </thead>
                      <tbody class="divide-y divide-gray-200 bg-white">
                        {data.map((benchmark) => (
                          <tr key={benchmark.id}>
                            <td class="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-primary-navy sm:pl-6 lg:pl-8">
                              {benchmark.id}
                            </td>
                            <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                              {benchmark.name}
                            </td>
                            <td class="relative whitespace-nowrap py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6 lg:pr-8">
                              {benchmark.unit}
                            </td>
                            <td class="relative whitespace-nowrap py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6 lg:pr-8">
                              <Link to={`/graph/${benchmark.id}`}>Graph</Link>
                            </td>
                            <td class="relative whitespace-nowrap py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6 lg:pr-8">
                              <Link to={`/table/${benchmark.id}`}>Table</Link>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </main>
  );
};

export default HomePage;
