import { Route, Routes } from "react-router-dom";
import { AppBar } from "./components";
import SettingsPage from "./pages/settings-page";
import BenchmarkPage from "./pages/benchmarks-master-page";
import EnvironmentsPage from "./pages/environments-master-page";
import EnvironmentDetails from "./pages/environment-details-page";
import BenchmarkDetailsGraph from "./pages/benchmark-details-chart-page";
import BenchmarkDetailsTable from "./pages/benchmark-details-table-page";
import "./css/style.css";
import CreateEnvironment from "./pages/create-environment-page";

function App() {
  return (
    <div>
      <div className="bg-primary-gray">
        <AppBar />
        <div className="xl:pl-64">
          <main className="w-full xl:px-4">
            <div className="bg-white xl:rounded-sm xl:shadow-sm h-full">
              <Routes>
                <Route path="/" element={<BenchmarkPage />} />
                <Route path="/settings" element={<SettingsPage />} />
                <Route path="/benchmarks" element={<BenchmarkPage />} />
                <Route
                  path="/benchmark/graph/:id"
                  element={<BenchmarkDetailsGraph />}
                />
                <Route
                  path="/benchmark/table/:id"
                  element={<BenchmarkDetailsTable />}
                />
                <Route path="/environments" element={<EnvironmentsPage />} />
                <Route
                  path="/environment/create"
                  element={<CreateEnvironment />}
                />
                <Route
                  path="/environment/:id"
                  element={<EnvironmentDetails />}
                />
                <Route path="/environment" element={<EnvironmentDetails />} />
              </Routes>
            </div>
          </main>
        </div>
      </div>
    </div>
  );
}

export default App;
