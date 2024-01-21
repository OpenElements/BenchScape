import React from "react";
import { useParams } from "react-router-dom";
import { useMeasurements, useMeasurementsSmooth } from "../../hooks";
import GraphCard from "../../charts/GraphCard";

const MeasurementsGraphComponent = ({ type }) => {
  const { id } = useParams();
  const realData = useMeasurements(id);
  const smoothData = useMeasurementsSmooth(id);

  if (realData.isLoading) {
    return <div>Loading...</div>;
  }

  let graphData = {
    datasets: [
      {
        label: `REAL DATA`,
        data: realData.data?.map((d) => d.value),
      },
      {
        label: `SMOOTH DATA`,
        data: smoothData.data?.map((d) => d.value),
      },
      {
        label: `SMOOTH DATA MIN`,
        data: realData.data?.map((d) => d.value),
      },
      {
        label: `SMOOTH DATA MAX`,
        data: realData.data?.map((d) => d.value),
      },
    ],
    timeStamps: realData.data?.map((d) => d.timestamp),
  };

  console.log(graphData.datasets, "DATATS");

  // let plugins = [];

  // var options = {
  //   maintainAspectRatio: false,
  //   scales: {
  //     x: { type: "time" },
  //   },
  // };

  return (
    <div>
      <GraphCard data={graphData.datasets} timeStamps={graphData.timeStamps} />
    </div>
  );
};

export default MeasurementsGraphComponent;
