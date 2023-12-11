import React from "react";
import "chartjs-adapter-date-fns";
import {useMeasurements, useMeasurementsSmooth,} from "../../hooks/hooks";
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
  Tooltip,
} from "chart.js";
import {enUS} from "date-fns/locale";
import {Line} from "react-chartjs-2";

const MeasurementsGraphComponent = ({type}) => {
  const {id} = useParams();
  const realData = useMeasurements(id);
  const smoothData = useMeasurementsSmooth(id);

  // const loess10 = useMeasurementsInterpolated(id, 'LOESS', 10);
  // const loess100 = useMeasurementsInterpolated(id, 'LOESS', 100);
  // const loess1000 = useMeasurementsInterpolated(id, 'LOESS', 1000);

  // const spline10 = useMeasurementsInterpolated(id, 'SPLINE', 10);
  // const spline100 = useMeasurementsInterpolated(id, 'SPLINE', 100);
  // const spline1000 = useMeasurementsInterpolated(id, 'SPLINE', 1000);

  // const linear10 = useMeasurementsInterpolated(id, 'LINEAR', 10);
  // const linear100 = useMeasurementsInterpolated(id, 'LINEAR', 100);
  // const linear1000 = useMeasurementsInterpolated(id, 'LINEAR', 1000);

  if (realData.isLoading) {
    return <div>Loading...</div>;
  }

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
        showLine: false,
      },
      {
        label: `SMOOTH DATA`,
        data: smoothData.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        borderColor: "#4233c9",
        pointStyle: false,
        backgroundColor: '#f50320',
        fill: 'origin'
      },
      {
        label: `SMOOTH DATA MIN`,
        data: smoothData.data?.map((d) => ({x: d.timestamp, y: d.min})),
        borderWidth: 2,
        borderColor: '#f50320',
        pointStyle: false,
        backgroundColor: '#f50320',
      },
      {
        label: `SMOOTH DATA MAX`,
        data: smoothData.data?.map((d) => ({x: d.timestamp, y: d.max})),
        borderWidth: 2,
        borderColor: '#ef0505',
        pointStyle: false,
        backgroundColor: '#f50320',
        fill: {value: 25}
      }
      /*
      {
        label: `LOESS 10`,
        data: loess10.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#40de8a',
      },
      {
        label: `LOESS 100`,
        data: loess100.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#36A2EB',
      },
      {
        label: `LOESS 1000`,
        data: loess1000.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#9b821f',
      },
      {
        label: `SPAN 10`,
        data: spline10.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#40de8a',
      },
      {
        label: `SPAN 100`,
        data: spline100.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#36A2EB',
      },
      {
        label: `SPAN 1000`,
        data: spline1000.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#9b821f',
      },
      {
        label: `LINEAR 10`,
        data: linear10.data?.map((d) => ({x: d.timestamp, y: d.value})),
        borderWidth: 2,
        cubicInterpolationMode: 'monotone',
        tension: 0.4,
        borderColor: '#40de8a',
      },
         {
           label: `LINEAR 100`,
           data: linear100.data?.map((d) => ({x: d.timestamp, y: d.value})),
           borderWidth: 2,
           cubicInterpolationMode: 'monotone',
           tension: 0.4,
           borderColor: '#36A2EB',
         },
         {
           label: `LINEAR 1000`,
           data: linear1000.data?.map((d) => ({x: d.timestamp, y: d.value})),
           borderWidth: 2,
           cubicInterpolationMode: 'monotone',
           tension: 0.4,
           borderColor: '#9b821f',
         },'*/,
    ],
  };

  var options = {
    maintainAspectRatio: false,
    scales: {
      y: {},
      x: {
        type: "time",
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
