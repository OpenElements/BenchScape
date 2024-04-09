export type Environment = {
  id: string;
  name: string;
  description: string;
  gitOriginUrl: string;
  gitBranch: string;
  systemArch: string | null;
  systemProcessors: number | null;
  systemProcessorsMin: number | null;
  systemProcessorsMax: number | null;
  systemMemory: number | null;
  systemMemoryMin: number | null;
  systemMemoryMax: number | null;
  systemMemoryReadable: string | null;
  systemMemoryMinReadable: string | null;
  systemMemoryMaxReadable: string | null;
  osName: string;
  osVersion: string;
  osFamily: string;
  jvmVersion: string | null;
  jvmName: string | null;
  jmhVersion: string | null;
};

export type Benchmark = {
  id: string;
  name: string;
  params: Object;
  tags: Array<string>;
};

export type Measurement = {
  id: string;
  timestamp: string;
  value: number;
  error: number;
  min: number;
  max: number;
  unit: string;
};

export type EnvironmentFilters = {
  osFamily: string;
  osVersion: string;
  systemArch: string;
  cores: string;
  memory: string;
  jvmName: string;
  jvmVersion: string;
  jmhVersion: string;
};

export type EnvironmentsResponse = Array<Environment>;
export type BenchmarksResponse = Array<Benchmark>;
export type MeasurementsResponse = Array<Measurement>;
