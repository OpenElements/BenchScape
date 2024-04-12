import { useEffect } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { useOSFamily, useEnvironmentMetadata } from "../hooks";
import { createAppBarConfig } from "../utils";
import { z } from "zod";
import { Select } from "../components";
import { saveEnvironment } from "../api";

const schema = z.object({
  infraName: z.string(),
  infraDescription: z.string(),
  osName: z.string(),
  osVersion: z.string(),
  cores: z.string(),
  memory: z.string(),
  java: z.string(),
  jmh: z.string(),
  jvm: z.string(),
  systemArch: z.string(),
});

type EnvironmentFormData = z.infer<typeof schema>;

const inputClasses =
  "w-24 text-center text-sm border-0 border-b border-gray-200 focus:border-0 focus:border-b focus:border-gray-400";

const CreateEnvironment = () => {
  const { data: osFamilyOptions } = useOSFamily();
  const { data: archOptions } = useEnvironmentMetadata("arch");
  const { data: jvmNameOptions } = useEnvironmentMetadata("jvmName");
  const { handleSubmit, register, control } = useForm<EnvironmentFormData>({
    mode: "all",
    resolver: zodResolver(schema),
  });

  const navigate = useNavigate();

  const onSubmit = async (data: EnvironmentFormData) => {
    const {
      infraName,
      infraDescription,
      memory,
      jmh,
      java,
      cores,
      osName,
      osVersion,
      jvm,
      systemArch,
    } = data;

    const payload = {
      osName,
      osVersion: osVersion,
      name: infraName,
      description: infraDescription,
      systemMemory: Number(memory),
      jmhVersion: jmh,
      jvmVersion: java,
      systemProcessors: Number(cores),
      systemArch: systemArch,
      jvmName: jvm, 
    };
    await saveEnvironment(payload).then(() => navigate("/environments"));
  };

  const onError = (err) => console.error(err);

  useEffect(() => {
    createAppBarConfig({
      title: "Create Environment",
      actions: [],
    });
  }, []);

  return (
    <form onSubmit={handleSubmit(onSubmit, onError)}>
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
                      options={osFamilyOptions?.filter(
                        (option) => option !== "UNKNOWN"
                      )}
                      value={field.value !== "UNKNOWN" ? field.value : null}
                      valueExtractor={(name) => name}
                      labelExtractor={(label) => {
                        if (label === "MAC_OS") {
                          return "Mac OS";
                        } else {
                          return (
                            label.charAt(0).toUpperCase() +
                            label.slice(1).toLowerCase()
                          );
                        }
                      }}
                      onChange={(e) => field.onChange(e)}
                    />
                  )}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Os Version</div>
              <div className="text-sm text-gray-800">
                <input
                  type="text"
                  {...register("osVersion")}
                  className={inputClasses}
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
              <div className="text-sm text-gray-800 flex items-center">
                <input
                  type="text"
                  {...register("memory")}
                  className={inputClasses}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Jvm Name</div>
              <div className="text-sm text-gray-800">
                <Controller
                  name="jvm"
                  control={control}
                  render={({ field }) => (
                    <Select
                      options={jvmNameOptions}
                      value={field.value}
                      valueExtractor={(name) => name}
                      labelExtractor={(label) => label }
                      onChange={(e) => field.onChange(e)}
                    />
                  )}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">Java</div>
              <div className="text-sm text-gray-800 flex items-center">
                <input
                  type="text"
                  {...register("java")}
                  className={inputClasses}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">System Arch</div>
              <div className="text-sm text-gray-800">
                <Controller
                  name="systemArch"
                  control={control}
                  render={({ field }) => (
                    <Select
                      options={archOptions}
                      value={field.value}
                      valueExtractor={(name) => name}
                      labelExtractor={(label) => label }
                      onChange={(e) => field.onChange(e)}
                    />
                  )}
                />
              </div>
            </div>
            <div className="flex justify-between items-center">
              <div className="text-sm text-gray-800">JMH</div>
              <div className="text-sm text-gray-800 flex items-center">
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

export default CreateEnvironment;
