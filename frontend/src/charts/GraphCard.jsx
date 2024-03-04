import React from "react";
import LineChart from "./LineChart";
import { tailwindConfig } from "./utils/Utils";

function GraphCard({ data = [], timeStamps = [] }) {
  const real = data.find((d) => d.label === "REAL DATA");
  const realMin = data.find((d) => d.label === "REAL DATA MIN");
  const realMax = data.find((d) => d.label === "REAL DATA MAX");
  const realError = data.find((d) => d.label === "REAL DATA ERROR");
  const smooth = data.find((d) => d.label === "SMOOTH DATA");
  const smoothMin = data.find((d) => d.label === "SMOOTH DATA MIN");
  const smoothMax = data.find((d) => d.label === "SMOOTH DATA MAX");
  const smoothError = data.find((d) => d.label === "SMOOTH DATA ERROR");

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
      {
        ...realMin,
        borderColor: tailwindConfig().theme.colors.orange[400],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.orange[400],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.orange[400],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      {
        ...realMax,
        borderColor: tailwindConfig().theme.colors.orange[400],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.orange[400],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.orange[400],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      {
        ...realError,
        borderColor: tailwindConfig().theme.colors.red[400],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.red[400],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.red[400],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      {
        ...smooth,
        borderColor: tailwindConfig().theme.colors.blue[600],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.blue[600],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.blue[600],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      // emerald line
      {
        ...smoothMin,
        borderColor: tailwindConfig().theme.colors.orange[500],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.orange[500],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.orange[500],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      {
        ...smoothMax,
        borderColor: tailwindConfig().theme.colors.orange[500],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.orange[500],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.orange[500],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
      {
        ...smoothError,
        borderColor: tailwindConfig().theme.colors.red[500],
        fill: false,
        borderWidth: 2,
        tension: 0,
        pointRadius: 0,
        pointHoverRadius: 3,
        pointBackgroundColor: tailwindConfig().theme.colors.red[500],
        pointHoverBackgroundColor: tailwindConfig().theme.colors.red[500],
        pointBorderWidth: 0,
        pointHoverBorderWidth: 0,
        clip: 20,
      },
    ],
  };

  return <LineChart data={chartData} />;
}

export default GraphCard;
