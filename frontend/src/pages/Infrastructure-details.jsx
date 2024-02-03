import React from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useEnvironmentById } from "../hooks";
import { z } from "zod";
import Select from "../components/tags/select";

const schema = z.object({
  infraName: z.string(),
  infraDescription: z.string(),
  osName: z.array(z.string()),
  cores: z.string(),
  memory: z.string(),
  java: z.string(),
  jmh: z.string(),
});

const InfrastructureDetails = () => {
  const { id } = useParams();
  const { data } = useEnvironmentById(id);
  const { handleSubmit, register } = useForm({
    mode: "onChange",
    resolver: zodResolver(schema),
    defaultValues: {
      infraName: data?.name ?? "",
      infraDescription: data?.description ?? "",
      osName: data?.osName ?? "",
      cores: data?.systemProcessors ?? "",
      memory: data?.systemMemory ?? "",
      java: data?.jvmVersion ?? "",
      jmh: data?.jmhVersion ?? "",
    },
  });

  console.log(data);

  const navigate = useNavigate();

  const onSubmit = (data) => {
    console.log(data);
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
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
              {...register("infraName")}
            />
          </div>
          <div className="flex flex-col gap-1 max-w-screen-sm">
            <label className="text-xs text-gray-800">
              Infrastructure description
            </label>
            <textarea
              className="border-2 border-slate-300 rounded"
              type="text"
              {...register("infraDescription")}
            />
          </div>
        </div>
        <div style={{ maxWidth: "400px" }} className="ml-2">
          <p className="font-bold">Infrastructure</p>
          <div className="flex flex-col gap-8 mt-4">
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Operating System</div>
              <div className="text-sm text-gray-800">
                <Select options={} />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">CPU cores</div>
              <div className="text-sm text-gray-800">
                <input type="text" {...register("cores")} />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Memory</div>
              <div className="text-sm text-gray-800">
                <input type="text" {...register("memory")} />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Java</div>
              <div className="text-sm text-gray-800">
                <input type="text" {...register("java")} />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">JMH</div>
              <div className="text-sm text-gray-800">
                <input type="text" {...register("jmh")} />
              </div>
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
    </form>
  );
};

export default InfrastructureDetails;
