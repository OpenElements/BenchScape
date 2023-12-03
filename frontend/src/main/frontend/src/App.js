import { Route, Routes } from "react-router-dom";
import {
  SideNav,
  MeasurementsGraphComponent,
  MeasurementsTableComponent,
} from "./components";
// import SideNav from "./components/side-nav.component";
import HomePage from "./pages/home-page";
import SettingsPage from "./pages/settings-page";
// import TimeSeriesTableComponent from "./components/time-series-table.component";
// import TimeSeriesGraphComponent from "./components/time-series-graph.component";
import BenchmarkPage from "./pages/benchmarks-page";
import EnvironmentsPage from "./pages/environments-page";
import EnvSettings from "./pages/env-settings";
import TopNav from "./components/topNav/top-nav.component";

function App() {
  return (
    <div>
      <SideNav />
        <div className="xl:pl-64 bg-primary-gray">
          <main className="w-full xl:p-4 h-screen">
            <div className="bg-white xl:rounded-sm overflow-hidden xl:shadow-sm h-full">
              <TopNav />
              <div className="">
                <Routes>
                  <Route path="/" element={<HomePage />} />
                  <Route path="/settings" element={<SettingsPage />} />
                  <Route path="/benchmarks" element={<BenchmarkPage />} />
                  <Route path="/environment" element={<EnvironmentsPage />} />
                  <Route path="/env-settings" element={<EnvSettings />} />
                  <Route
                    path="/table/:id"
                    element={<MeasurementsTableComponent type="table" />}
                  />
                  <Route
                    path="/graph/:id"
                    element={<MeasurementsGraphComponent type="graph" />}
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
