import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useEnvironmentById } from "../hooks";

const InfrastructureDetails = () => {
  const { id } = useParams();
  const { data } = useEnvironmentById(id);

  console.log(data);
  const navigate = useNavigate();
  return (
    <div className="p-8 flex flex-col gap-6">
      <div>
        <h1 className="h4">BenchScape V1.1</h1>
        <h2 className="text-gray-900">
          Lorem ipsum dolor sit amet consectetur adipisicing elit.
        </h2>
      </div>
      <div className="flex flex-col gap-3 ml-2">
        <div className="flex flex-col gap-1" style={{ maxWidth: "400px" }}>
          <label className="text-xs text-gray-800">Infrastructure name</label>
          <input
            className="border-2 border-slate-300 rounded"
            type="text"
            name="infrastructure"
          />
        </div>
        <div className="flex flex-col gap-1 max-w-screen-sm">
          <label className="text-xs text-gray-800">
            Infrastructure description
          </label>
          <textarea
            className="border-2 border-slate-300 rounded"
            type="text"
            name="infrastructure-desc"
          />
        </div>
      </div>
      <div style={{ maxWidth: "400px" }} className="ml-2">
        <p className="font-bold">Infrastructure</p>
        <div className="flex flex-col gap-8 mt-4">
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Operating System</div>
            <div className="text-sm text-gray-800">{data?.osName ?? "_"}</div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">CPU cores</div>
            <div className="text-sm text-gray-800">
              {data?.systemProcessors ?? "_"}
            </div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Memory</div>
            <div className="text-sm text-gray-800">
              {data?.systemMemory ?? "_"}
            </div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Java</div>
            <div className="text-sm text-gray-800">
              {data?.jmhVersion ?? "_"}
            </div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">JMH</div>
            <div className="text-sm text-gray-800">
              {data?.jmhVersion ?? "_"}
            </div>
          </div>
        </div>
      </div>
      <div style={{ maxWidth: "400px" }}>
        <p className="font-bold">Measurement</p>
        <div className="flex flex-col gap-5 mt-4">
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Threads</div>
            <div className="text-sm text-gray-800">Value</div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Forks</div>
            <div className="text-sm text-gray-800">Value</div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Warmup</div>
            <div className="text-sm text-gray-800">Value</div>
          </div>
          <div className="flex justify-between items-center">
            <div className="text-sm text-gray-800">Measurement</div>
            <div className="text-sm text-gray-800">Value</div>
          </div>
        </div>
      </div>
      <div className="flex self-end gap-2">
        <button
          className="border-2 text-sm rounded-md text-gray-800"
          style={{
            padding: "6px 18px",
          }}
          onClick={() => navigate(-1)}
        >
          Cancel
        </button>
        <button
          className="border-2 text-sm rounded-md bg-indigo-700 border-indigo-700 text-white"
          style={{
            padding: "6px 30px",
          }}
        >
          Save
        </button>
      </div>
    </div>
  );
};

export default InfrastructureDetails;
