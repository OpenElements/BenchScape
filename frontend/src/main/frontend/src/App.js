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

function App() {
  return (
    <div className="bg-primary-gray w-full min-h-screen">
      <SideNav />
      <div className="xl:pl-72">
        <main className="w-full h-full px-4 sm:px-6 xl:px-7 xl:pb-5">
          <div className="bg-white rounded-lg w-full h-full shadow-lg">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/settings" element={<SettingsPage />} />
              <Route path="/benchmarks" element={<BenchmarkPage />} />
              <Route path="/environment" element={<EnvironmentsPage />} />
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
        </main>
      </div>
    </div>
  );
}

export default App;
