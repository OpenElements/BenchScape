import React, { useEffect } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate, useParams } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { useEnvironmentById, useOS } from "../hooks";
import { z } from "zod";
import { Select } from "../components";
import { saveEnvironment } from "../api";

const schema = z.object({
  infraName: z.string(),
  infraDescription: z.string(),
  osName: z.string(),
  cores: z.string(),
  memory: z.string(),
  java: z.string(),
  jmh: z.string(),
});

const inputClasses =
  "w-24 text-center text-sm border-0 border-b border-gray-200 focus:border-0 focus:border-b focus:border-gray-400";

const InfrastructureDetails = () => {
  const { id } = useParams();
  const { data, isLoading } = useEnvironmentById(id);
  const { data: osOptions } = useOS();
  const { handleSubmit, register, control, reset } = useForm({
    mode: "onChange",
    resolver: zodResolver(schema),
  });

  const navigate = useNavigate();

  const onSubmit = async (data) => {
    const { infraName, infraDescription, memory, jmh, java, cores, osName } =
      data;

    const payload = {
      id,
      osName,
      name: infraName,
      description: infraDescription,
      systemMemoryReadable: memory,
      jmhVersion: jmh,
      jvmVersion: java,
      systemProcessors: cores,
    };
    await saveEnvironment(payload).then(() => navigate("/environments"));
  };

  useEffect(() => {
    if (!isLoading) {
      reset({
        infraName: data?.name || "",
        infraDescription: data?.description || "",
        osName: data?.osName || "",
        cores: data?.systemProcessors || "",
        memory: data?.systemMemoryReadable || "",
        java: data?.jvmVersion || "",
        jmh: data?.jmhVersion || "",
      });
    }
  }, [
    data?.description,
    data?.jmhVersion,
    data?.jvmVersion,
    data?.name,
    data?.osName,
    data?.systemMemoryReadable,
    data?.systemProcessors,
    isLoading,
    reset,
  ]);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="p-8 flex flex-col gap-6">
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
                <Controller
                  name="osName"
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={osOptions}
                      labelExtractor={(label) => label}
                      valueExtractor={(value) => value}
                    />
                  )}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">CPU cores</div>
              <div className="text-sm text-gray-800">
                <input
                  type="text"
                  {...register("cores")}
                  className={inputClasses}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Memory</div>
              <div className="text-sm text-gray-800">
                <input
                  type="text"
                  {...register("memory")}
                  className={inputClasses}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Java</div>
              <div className="text-sm text-gray-800">
                <input
                  type="text"
                  {...register("java")}
                  className={inputClasses}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">JMH</div>
              <div className="text-sm text-gray-800">
                <input
                  type="text"
                  {...register("jmh")}
                  className={inputClasses}
                />
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
            type="submit"
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
