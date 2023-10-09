import React from "react";
import { useTimeSeries } from "../hooks/hooks";
import { useParams } from "react-router-dom";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

import { Line } from "react-chartjs-2";

const TimeSeriesGraphComponent = ({ type }) => {
  const { id } = useParams();
  const { data, isLoading } = useTimeSeries(id);

  console.log(data, "getting data");
  if (isLoading) {
    return <div>Loading...</div>;
  }

  // import React, { useState, useEffect } from "react";

  ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
  );

  let graphData = {
    labels: data?.map((x) => {
      return new Date(x.timestamp).toLocaleString("default", {
        day: "numeric",
        year: "numeric",
        month: "short",
      });
    }),
    datasets: [
      {
        label: `OPERATIONS_PER_MILLISECOND`,
        data: data?.map((x) => x.min),
        backgroundColor: [
          "rgba(255, 99, 132, 0.2)",
          "rgba(54, 162, 235, 0.2)",
          "rgba(255, 206, 86, 0.2)",
          "rgba(75, 192, 192, 0.2)",
          "rgba(153, 102, 255, 0.2)",
          "rgba(255, 159, 64, 0.2)",
        ],
        borderColor: [
          "rgba(255, 99, 132, 1)",
          "rgba(54, 162, 235, 1)",
          "rgba(255, 206, 86, 1)",
          "rgba(75, 192, 192, 1)",
          "rgba(153, 102, 255, 1)",
          "rgba(255, 159, 64, 1)",
        ],
        borderWidth: 1,
      },
    ],
  };

  var options = {
    maintainAspectRatio: false,
    scales: {
      y: {},
      x: {
        stepSize: 5,
      },
    },
    legend: {
      labels: {
        fontSize: 25,
      },
    },
  };

  return (
    <div>
      <Line data={graphData} height={800} options={options} />
    </div>
  );
};

export default TimeSeriesGraphComponent;
