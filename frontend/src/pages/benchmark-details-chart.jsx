import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useEnvironments, useMeasurements } from "../hooks";
import Datepicker from "../components/DatePicker";
import Select from "../components/Select";
import GraphCard from "../charts/GraphCard";
import Checkbox from "../components/Checkbox";

const BenchmarksDetailsGraph = () => {
  const { id } = useParams();
  const [filters, setFilters] = useState({
    benchmarkId: id,
    environmentIds: "",
    start: "",
    end: "",
  });

  const [checks, setChecks] = useState({
    showRealData: true,
    showRealDataMin: false,
    showRealDataMax: false,
    showRealDataError: false,
    showSmoothData: true,
    showSmoothDataMin: false,
    showSmoothDataMax: false,
    showSmoothDataError: false,
  });

  const {
    showRealData,
    showRealDataMin,
    showRealDataMax,
    showRealDataError,
    showSmoothData,
    showSmoothDataMin,
    showSmoothDataMax,
    showSmoothDataError,
  } = checks;
  const { data: environments } = useEnvironments();
  const { data: measurements, isLoading } = useMeasurements(filters);
  const { data: smoothData } = useMeasurements({
    benchmarkId: id,
    smooth: true,
  });

  const handleChecked = (field, checked) =>
    setChecks((prev) => ({ ...prev, [field]: checked }));

  let graphData = {
    datasets: [
      ...(showRealData
        ? [
            {
              label: `REAL DATA`,
              data: measurements?.map((d) => d.value),
              // key: 'showRealData'
            },
          ]
        : []),

      ...(showRealDataMin
        ? [
            {
              label: `REAL DATA MIN`,
              data: measurements?.map((d) => d.min),
              // key: 'showRealDataMin'
            },
          ]
        : []),

      ...(showRealDataMax
        ? [
            {
              label: `REAL DATA MAX`,
              data: measurements?.map((d) => d.max),
              // key: 'showRealDataMax'
            },
          ]
        : []),

      ...(showRealDataError
        ? [
            {
              label: `REAL DATA ERROR`,
              data: measurements?.map((d) => d.error),
              // key: 'showRealDataError'
            },
          ]
        : []),

      ...(showSmoothData
        ? [
            {
              label: `SMOOTH DATA`,
              data: smoothData?.map((d) => d.value),
              // key: 'showSmoothData'
            },
          ]
        : []),

      ...(showSmoothDataMin
        ? [
            {
              label: `SMOOTH DATA MIN`,
              data: smoothData?.map((d) => d.min),
              // key: 'showSmoothDataMin'
            },
          ]
        : []),

      ...(showSmoothDataMax
        ? [
            {
              label: `SMOOTH DATA`,
              data: smoothData?.map((d) => d.max),
              // key: 'showSmoothData'
            },
          ]
        : []),

      ...(showSmoothDataError
        ? [
            {
              label: `SMOOTH DATA`,
              data: smoothData?.map((d) => d.error),
              // key: 'showSmoothData'
            },
          ]
        : []),
    ],
    timeStamps: [
      ...(measurements ? measurements?.map((d) => d.timestamp) : []),
      ...(smoothData ? smoothData?.map((d) => d.timestamp) : []),
    ],
  };

  const handDateChange = (field, date) => {
    setFilters((prev) => ({ ...prev, [field]: date }));
  };

  const handleChage = (field, { target }) => {
    setFilters((prev) => ({ ...prev, [field]: target.value }));
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="w-full h-full flex flex-col col-span-full sm:col-span-6 bg-white shadow-lg rounded-sm border border-slate-200 dark:border-slate-100 mt-4">
      <div className="flex justify-around items-center gap-2 m-4">
        <Select
          label="Environment"
          options={environments}
          value={filters.environmentIds}
          valueExtractor={(option) => option.id}
          labelExtractor={(option) => option.name}
          onChange={(e) => handleChage("environmentIds", e)}
        />
        <div className="flex gap-2">
          <Datepicker
            label="Start Date"
            value={filters.start}
            onChange={(date) => handDateChange("start", date)}
          />
          <Datepicker
            label="End Date"
            value={filters.end}
            onChange={(date) => handDateChange("end", date)}
          />
        </div>
        <div className="grid grid-cols-4 gap-y-2">
          <Checkbox
            label="Show Value"
            name="ShowValue"
            value={checks.showRealDataMin}
            onChange={(e) => handleChecked("showRealData", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Show Minimum"
            name="ShowMin"
            value={checks.showRealDataMin}
            onChange={(e) => handleChecked("showRealDataMin", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label=" Show Maximum"
            name="ShowMax"
            value={checks.showRealDataMax}
            onChange={(e) => handleChecked("showRealDataMax", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Show Error"
            name="ShowError"
            value={checks.showRealDataError}
            onChange={(e) =>
              handleChecked("showRealDataError", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Show Smooth"
            name="ShowSmooth"
            value={checks.showRealDataMin}
            onChange={(e) => handleChecked("showSmoothData", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Smooth Min"
            name="ShowSmoothMin"
            value={checks.showRealDataMin}
            onChange={(e) =>
              handleChecked("showSmoothDataMin", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Smooth Max"
            name="ShowSmoothMax"
            value={checks.showRealDataMin}
            onChange={(e) =>
              handleChecked("showSmoothDataMax", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Smooth Error"
            name="ShowSmoothError"
            value={checks.showRealDataMin}
            onChange={(e) =>
              handleChecked("showSmoothDataError", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
        </div>
        {/* <Select
        label="Units"
        options={["options"]}
        valueExtractor={(name) => name}
        labelExtractor={(name) => name}
      /> */}
      </div>
      <GraphCard data={graphData.datasets} timeStamps={graphData.timeStamps} />
    </div>
  );
};

export default BenchmarksDetailsGraph;
