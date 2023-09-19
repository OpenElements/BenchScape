import { Route, Routes } from "react-router-dom";
import SideNav from "./components/side-nav.component";
//import TopNav from "./components/top-nav.component";
import HomePage from "./pages/home-page";
import SettingsPage from "./pages/settings-page";
import AllertsPage from "./pages/allerts-page";
import FavoritesPage from "./pages/favourites-page";
import AnalyticsPage from "./pages/analystics-page";
import RegularUpdatesPage from "./pages/regular-update-page";
import TimeSeriesComponent from "./components/time-series.component";

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
              <Route path="/allerts" element={<AllertsPage />} />
              <Route path="/favorites" element={<FavoritesPage />} />
              <Route path="/analystics" element={<AnalyticsPage />} />
              <Route path="/regular-updates" element={<RegularUpdatesPage />} />
              <Route
                path="/graph/:id"
                element={<TimeSeriesComponent type="graph" />}
              />
              <Route
                path="/table/:id"
                element={<TimeSeriesComponent type="table" />}
              />
            </Routes>
          </div>
        </main>
      </div>
    </div>
  );
}

export default App;

// const Graph = () => {
//   const [graphDocument, setGraphDocument] = useState(null);
//   const { id } = useParams();
//   const env = process.env.REACT_APP_API_URL;
//   const url = `${env}/timeseries/graph?id=${id}`;

//   useEffect(() => {
//     getData(url)
//       .then((data) => setGraphDocument(data))
//       .catch((error) => {
//         console.error("Error fetching data:", error);
//       });
//   }, [url]);

//   return (
//     <React.Fragment>
//       {graphDocument && (
//         <div dangerouslySetInnerHTML={{ __html: graphDocument }}></div>
//       )}
//     </React.Fragment>
//   );
// };

// const Table = () => {
//   const [tableDocument, setTableDocument] = useState(null);
//   const { id } = useParams();
//   const env = process.env.REACT_APP_API_URL;
//   const tableUrl = `${env}/timeseries/table?id=${id}`;

//   useEffect(() => {
//     getData(tableUrl)
//       .then((data) => setTableDocument(data))
//       .catch((error) => {
//         console.error("Error fetching data:", error);
//       });
//   }, [tableUrl]);

//   return (
//     <React.Fragment>
//       {tableDocument && (
//         <div dangerouslySetInnerHTML={{ __html: tableDocument }}></div>
//       )}
//     </React.Fragment>
//   );
// };

// async function getData(url) {
//   try {
//     const response = await axios.get(url);
//     return response.data;
//   } catch (error) {
//     console.error("Error fetching data:", error);
//     throw error;
//   }
// }
