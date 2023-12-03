import { Link } from "react-router-dom";
import { useBenchMarks } from "../hooks/hooks";

const HomePage = () => {
  const { data, isLoading } = useBenchMarks();
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
                        <th scope="col" className="py-3.5 px-4 text-left text-sm font-medium text-gray-500 uppercase">
                          ID
                        </th>
                        <th scope="col" className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase">
                          Name
                        </th>
                        <th scope="col" className="py-3.5 px-4 text-left text-sm font-semibold text-gray-500 uppercase">
                          Unit
                        </th>
                        <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-right">
                          Graph
                        </th>
                        <th scope="col" className="py-3.5 px-4 text-sm font-semibold text-gray-500 uppercase text-right">
                          Table
                        </th>
                      </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-200 bg-white">
                      <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                            Transaction Benchmark
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          com.sample.transaction.Benchmark
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          15
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                        <a href="#" className="text-primary-green text-xs font-medium transition-colors ease-in-out duration-150">View Graph</a> 
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                          <a href="#" className="text-primary-green text-xs font-medium transition-colors ease-in-out duration-150">View Table</a>
                        </td>
                      </tr>
                      <tr className="group hover:bg-azure transition-colors ease-in-out duration-150">
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-medium text-gray-900">
                            Transaction Benchmark
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          com.sample.transaction.Benchmark
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                          15
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                        <a href="#" className="text-primary-green font-medium text-xs transition-colors ease-in-out duration-150">View Graph</a> 
                        </td>
                        <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500 text-right">
                          <a href="#" className="text-primary-green font-medium text-xs transition-colors ease-in-out duration-150">View Table</a>
                        </td>
                      </tr>
                    {/* {data.map((benchmark) => (
                    <tr key={benchmark.id} className="group hover:bg-azure transition-colors ease-in-out duration-150">
                      <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-900">
                        {benchmark.id}
                      </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                        {benchmark.name}
                      </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-sm font-light text-gray-500">
                        {benchmark.unit}
                      </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-xs font-light text-gray-500 text-right">
                        <Link to={`/graph/${benchmark.id}`}>Graph</Link>
                      </td>
                      <td className="whitespace-nowrap py-3.5 px-4 text-xs font-light text-gray-500 text-right">
                        <Link to={`/table/${benchmark.id}`}>Table</Link>
                      </td>
                    </tr>
                  ))} */}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default HomePage;
