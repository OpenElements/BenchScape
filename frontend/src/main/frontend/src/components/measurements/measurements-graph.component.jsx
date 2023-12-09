import React from "react";
import 'chartjs-adapter-date-fns';
import {useMeasurements, useMeasurementsInterpolated} from "../../hooks/hooks";
import {useParams} from "react-router-dom";
import {
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  TimeScale,
  Title,
  Tooltip
} from "chart.js";
import {enUS} from 'date-fns/locale';
import {Line} from "react-chartjs-2";

const MeasurementsGraphComponent = ({type}) => {
  const {id} = useParams();
  const realData = useMeasurements(id);

  const spline10 = useMeasurementsInterpolated(id, 'LOESS', 1000);
  const spline1000 = useMeasurementsInterpolated(id, 'LINEAR', 100);

  if (realData.isLoading || spline10.isLoading || spline1000.isLoading) {
    return <div>Loading...</div>;
  }
  console.log(spline10.data, "getting data");

  ChartJS.register(
      CategoryScale,
      LinearScale,
      PointElement,
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
        borderWidth: 4,
      },
      {
        label: `SPLINE 1`,
        data: spline10.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#99cd15',
      },
      {
        label: `SPLINE 2`,
        data: spline1000.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#36A2EB',
      },
    ],
  };

  var options = {
    maintainAspectRatio: false,
    scales: {
      y: {},
      x: {
        type: 'time',
        adapters: {
          date: {
            locale: enUS,
          },
        },
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
        <Line data={graphData} height={800} options={options}/>
      </div>
  );
};

export default MeasurementsGraphComponent;
