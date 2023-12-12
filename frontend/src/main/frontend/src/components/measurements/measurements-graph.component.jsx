import React from "react";
import "chartjs-adapter-date-fns";
import {useMeasurements, useMeasurementsSmooth,} from "../../hooks/hooks";
import {useParams} from "react-router-dom";
import {
  BarElement,
  CategoryScale,
  Chart as ChartJS,
  Filler,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  TimeScale,
  Title,
  Tooltip
} from "chart.js";
import {Line} from "react-chartjs-2";

const MeasurementsGraphComponent = ({type}) => {
  const {id} = useParams();
  const realData = useMeasurements(id);
  const smoothData = useMeasurementsSmooth(id);

  if (realData.isLoading) {
    return <div>Loading...</div>;
  }

  ChartJS.register(
      CategoryScale,
      LinearScale,
      Filler,
      PointElement,
      BarElement,
      LineElement,
      Title,
      Tooltip,
      Legend,
      TimeScale
  );

  let graphData = {
    datasets: [
      {
        label: `REAL DATA`,
        data: realData.data?.map((d) => ({x: d.timestamp, y: d.value})),
        showLine: false,
      },
      {
        label: `SMOOTH DATA`,
        data: smoothData.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderColor: "#4233c9",
        pointStyle: false
      },
      {
        label: `SMOOTH DATA MIN`,
        data: smoothData.data?.map((d) => ({x: d.timestamp, y: d.min})),
        borderColor: '#f50320',
        pointStyle: false,
      },
      {
        label: `SMOOTH DATA MAX`,
        data: smoothData.data?.map((d) => ({x: d.timestamp, y: d.max})),
        borderColor: '#ef0505',
        pointStyle: false,
        backgroundColor: 'rgba(255,192,78,0.8)',
        fill: '-1'
      }
    ],
  };

  let plugins = [];

  var options = {
    maintainAspectRatio: false,
    scales: {
      x: {type: "time"},
    }
  };

  return (
      <div>
        <Line data={graphData} height={600} plugins={plugins}
              options={options}/>
      </div>
  );
};

export default MeasurementsGraphComponent;
