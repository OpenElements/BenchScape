import { Route, Routes } from "react-router-dom";
import {
  SideNav,
  MeasurementsGraphComponent,
  MeasurementsTableComponent,
  TopNav,
} from "./components";
import SettingsPage from "./pages/settings-page";
import BenchmarkPage from "./pages/benchmarks-page";
import EnvironmentsPage from "./pages/environments-page";
import EnvSettings from "./pages/env-settings";
import { useState } from "react";
import "./css/style.css";
import InfrastructureDetails from "./pages/Infrastructure-details";
import BenchmarkDetails from "./pages/Benchmark-details";

function App() {
  const [sideBarOpen, setSideBarOpen] = useState(false);
  return (
    <div>
      <SideNav sidebarOpen={sideBarOpen} setSidebarOpen={setSideBarOpen} />
      <div className="xl:pl-64 bg-primary-gray">
        <main className="w-full xl:p-4">
          <div className="bg-white xl:rounded-sm xl:shadow-sm h-full">
            <TopNav setSidebarOpen={setSideBarOpen} />
            <div className="">
              <Routes>
                <Route path="/" element={<BenchmarkPage />} />
                <Route path="/settings" element={<SettingsPage />} />
                <Route path="/benchmarks" element={<BenchmarkPage />} />
                <Route path="/environments" element={<EnvironmentsPage />} />
                <Route path="/env-settings" element={<EnvSettings />} />
                <Route
                  path="/table/:id"
                  element={<MeasurementsTableComponent type="table" />}
                />
                <Route
                  path="/graph/:id"
                  element={<MeasurementsGraphComponent type="graph" />}
                />
                <Route
                  path="/environments/:id"
                  element={<InfrastructureDetails />}
                />
                <Route path="/benchmarks/:id" element={<BenchmarkDetails />} />
              </Routes>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}

export default App;
