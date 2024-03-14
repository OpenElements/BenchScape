import { Route, Routes } from "react-router-dom";
import { AppBar } from "./components";
import SettingsPage from "./pages/settings-page";
import BenchmarkPage from "./pages/benchmarks-master";
import EnvironmentsPage from "./pages/environments-master-page";
import EnvironmentDetails from "./pages/environment-details";
import BenchmarkDetailsGraph from "./pages/benchmark-details-chart";
import BenchmarkDetailsTable from "./pages/benchmark-details-table";
import "./css/style.css";

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
                  path="/benchmark/:id"
                  element={<BenchmarkDetailsGraph />}
                />
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
