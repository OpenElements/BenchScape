import React from "react";
import LineChart from "./LineChart";
import { tailwindConfig } from "./utils/Utils";
import Select from "../components/tags/select";
import Datepicker from "../components/datepicker/DatePicker";

function DashboardCard({ data = [], timeStamps }) {
  const smooth = data.find((d) => d.label === "SMOOTH DATA");
  const real = data.find((d) => d.label === "REAL DATA");
  const smoothMin = data.find((d) => d.label === "SMOOTH DATA MIN");
  const smoothMax = data.find((d) => d.label === "SMOOTH DATA MAX");

  const labelDate = timeStamps.map((t) => {
    const parsedDate = new Date(t);

    const year = parsedDate.getFullYear();
    const month = String(parsedDate.getMonth() + 1).padStart(2, "0");
    const day = String(parsedDate.getDate()).padStart(2, "0");
    return `${month}-${day}-${year}`;
  });

  const chartData = {
    labels: labelDate,
    datasets: [
      // Indigo line
      {
        ...smooth,
        borderColor: tailwindConfig().theme.colors.indigo[500],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.indigo[500],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.indigo[500],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      // Blue line
      {
        ...real,
        borderColor: tailwindConfig().theme.colors.blue[400],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.blue[400],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.blue[400],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      // emerald line
      {
        ...smoothMin,
        borderColor: tailwindConfig().theme.colors.emerald[500],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.emerald[500],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.emerald[500],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      {
        ...smoothMax,
        borderColor: tailwindConfig().theme.colors.green[500],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.green[500],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.green[500],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
    ],
  };

  console.log(chartData.datasets, "sdfsdfgsdf");
  return (
    <div className="flex flex-col col-span-full sm:col-span-6 bg-white shadow-lg rounded-sm border border-slate-200 dark:border-slate-100 mt-4">
      <div className="flex gap-2 justify-around items-center mt-4">
        <Select label="Architecture" options={["options"]} />
        <div className="flex gap-4 ">
          <Datepicker label="Start Date" />
          <Datepicker label="End Date" />
        </div>
        <div className="flex gap-4 mt-5">
          <div className="flex items-center">
            <input
              name="ShowMin"
              type="checkbox"
              className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
            />
            <label htmlFor="ShowMin" className="ml-2 text-xs text-gray-500">
              Show Minimum
            </label>
          </div>
          <div className="flex items-center">
            <input
              name="ShowMax"
              type="checkbox"
              className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
            />
            <label htmlFor="ShowMax" className="ml-2 text-xs text-gray-500">
              Show Maximum
            </label>
          </div>
          <div className="flex items-center">
            <input
              name="ShowError"
              type="checkbox"
              className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
            />
            <label htmlFor="ShowError" className="ml-2 text-xs text-gray-500">
              Show Error
            </label>
          </div>
        </div>
        <Select label="Architecture" options={["options"]} />
      </div>
      <LineChart data={chartData} width={"auto"} height={"auto"} />
    </div>
  );
}

export default DashboardCard;
