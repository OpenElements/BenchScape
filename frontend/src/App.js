import { Route, Routes } from "react-router-dom";
import { useState } from "react";
import { AppBar, SideNav } from "./components";
import SettingsPage from "./pages/settings-page";
import BenchmarkPage from "./pages/benchmarks-master";
import EnvironmentsPage from "./pages/environments-master-page";
import EnvironmentDetails from "./pages/environment-details";
import BenchmarkDetailsGraph from "./pages/benchmark-details-chart";
import BenchmarkDetailsTable from "./pages/benchmark-details-table";
import "./css/style.css";

function App() {
  const [sideBarOpen, setSideBarOpen] = useState(false);
  return (
    <div>
      <SideNav sidebarOpen={sideBarOpen} setSidebarOpen={setSideBarOpen} />
      <div className="xl:pl-64 bg-primary-gray">
        <main className="w-full xl:p-4">
          <div className="bg-white xl:rounded-sm xl:shadow-sm h-full">
            <AppBar setSidebarOpen={setSideBarOpen} />
            <div>
              <Routes>
                <Route path="/" element={<BenchmarkPage />} />
                <Route path="/settings" element={<SettingsPage />} />
                <Route path="/benchmarks" element={<BenchmarkPage />} />
                <Route path="/environments" element={<EnvironmentsPage />} />
                <Route path="/table/:id" element={<BenchmarkDetailsTable />} />
                <Route path="/graph/:id" element={<BenchmarkDetailsGraph />} />
                <Route
                  path="/environments/:id"
                  element={<EnvironmentDetails />}
                />
              </Routes>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}

export default App;
