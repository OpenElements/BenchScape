import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useEnvironments, useMeasurements } from "../../hooks";
import Datepicker from "../../components/DatePicker";
import Select from "../../components/Select";
import GraphCard from "../../charts/GraphCard";
import Checkbox from "../../components/Checkbox";
import { createAppBarConfig } from "../../utils";

const BenchmarksDetailsGraph = () => {
  const { id } = useParams();
  const navigate = useNavigate();
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

  let datasets = [
    ...(showRealData
      ? [
          {
            label: `REAL DATA`,
            data: measurements?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.value,
            })),
            // key: 'showRealData'
          },
        ]
      : []),

    ...(showRealDataMin
      ? [
          {
            label: `REAL DATA MIN`,
            data: measurements?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.min,
            })),
            // key: 'showRealDataMin'
          },
        ]
      : []),

    ...(showRealDataMax
      ? [
          {
            label: `REAL DATA MAX`,
            data: measurements?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.max,
            })),
            // key: 'showRealDataMax'
          },
        ]
      : []),

    ...(showRealDataError
      ? [
          {
            label: `REAL DATA ERROR`,
            data: measurements?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.error,
            })),
            // key: 'showRealDataError'
          },
        ]
      : []),

    ...(showSmoothData
      ? [
          {
            label: `SMOOTH DATA`,
            data: smoothData?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.value,
            })),
            // key: 'showSmoothData'
          },
        ]
      : []),

    ...(showSmoothDataMin
      ? [
          {
            label: `SMOOTH DATA MIN`,
            data: smoothData?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.min,
            })),
            // key: 'showSmoothDataMin'
          },
        ]
      : []),

    ...(showSmoothDataMax
      ? [
          {
            label: `SMOOTH DATA MAX`,
            data: smoothData?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.max,
            })),
            // key: 'showSmoothData'
          },
        ]
      : []),

    ...(showSmoothDataError
      ? [
          {
            label: `SMOOTH DATA ERROR`,
            data: smoothData?.map((d) => ({
              x: new Date(d.timestamp),
              y: d.error,
            })),
            // key: 'showSmoothData'
          },
        ]
      : []),
  ];

  const handDateChange = (field: string, date: string) => {
    setFilters((prev) => ({ ...prev, [field]: date }));
  };

  const handleChange = (field: string, { target }) => {
    setFilters((prev) => ({ ...prev, [field]: target.value }));
  };

  useEffect(() => {
    createAppBarConfig({
      title: "Bechmark details",
      actions: [
        {
          name: "Table View",
          action: () => navigate(`/benchmark/table/${id}`),
        },
      ],
    });
    // eslint-disable-next-line
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="col-span-full mt-4 flex h-full w-full flex-col rounded-sm border border-slate-200 bg-white shadow-lg dark:border-slate-100 sm:col-span-6">
      <div className="m-4 flex items-center justify-around gap-2">
        <Select
          label="Environment"
          options={environments}
          value={filters.environmentIds}
          valueExtractor={(option) => option.id}
          labelExtractor={(option) => option.name}
          onChange={(e) => handleChange("environmentIds", e)}
        />
        <div className="flex gap-2">
          <Datepicker
            label="Start Date"
            placeholder="Enter start date"
            value={filters.start}
            onChange={(date) => handDateChange("start", date)}
          />
          <Datepicker
            label="End Date"
            placeholder="Enter end date"
            value={filters.end}
            onChange={(date) => handDateChange("end", date)}
          />
        </div>
        <div className="grid grid-cols-4 gap-y-2">
          <Checkbox
            defaultChecked
            label="Show Value"
            name="ShowValue"
            value={String(checks.showRealDataMin)}
            onChange={(e) => handleChecked("showRealData", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Show Minimum"
            name="ShowMin"
            value={String(checks.showRealDataMin)}
            onChange={(e) => handleChecked("showRealDataMin", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label=" Show Maximum"
            name="ShowMax"
            value={String(checks.showRealDataMax)}
            onChange={(e) => handleChecked("showRealDataMax", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Show Error"
            name="ShowError"
            value={String(checks.showRealDataError)}
            onChange={(e) =>
              handleChecked("showRealDataError", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            defaultChecked
            label="Show Smooth"
            name="ShowSmooth"
            value={String(checks.showRealDataMin)}
            onChange={(e) => handleChecked("showSmoothData", e.target.checked)}
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Smooth Min"
            name="ShowSmoothMin"
            value={String(checks.showRealDataMin)}
            onChange={(e) =>
              handleChecked("showSmoothDataMin", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Smooth Max"
            name="ShowSmoothMax"
            value={String(checks.showRealDataMin)}
            onChange={(e) =>
              handleChecked("showSmoothDataMax", e.target.checked)
            }
            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
          />
          <Checkbox
            label="Smooth Error"
            name="ShowSmoothError"
            value={String(checks.showRealDataMin)}
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
      <GraphCard data={datasets} />
    </div>
  );
};

export default BenchmarksDetailsGraph;
