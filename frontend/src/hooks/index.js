import useSwr from "swr";
import { dataFetcher } from "../api";

const apiUrl = process.env.REACT_APP_API_URL;

export function useBenchMarks() {
  return useSwr(`${apiUrl}/api/v2/benchmark/all`, dataFetcher);
}

export function useEnvironments(queries) {
  const withValues = Object.entries(queries)
    .filter(([key, value]) => Boolean(value))
    .map(([key, value]) => [key, value]);

  const params = new URLSearchParams(withValues).toString();

  return useSwr(
    `${apiUrl}/api/v2/environment/findByQuery?${params}`,
    dataFetcher
  );
}

export function useEnvironmentById(id) {
  return useSwr(`${apiUrl}/api/v2/environment/find?id=${id}`, dataFetcher);
}

export function useMeasurements(id) {
  return useSwr(
    `${apiUrl}/api/v2/measurement/find?benchmarkId=${id}`,
    dataFetcher
  );
}

export function useMeasurementsSmooth(id) {
  return useSwr(
    `${apiUrl}/api/v2/measurement/find?benchmarkId=${id}&smooth=true`,
    dataFetcher
  );
}

export function useMeasurementsInterpolated(id, interpolationType, points) {
  return useSwr(
    `${apiUrl}/api/v2/measurement/findInterpolated?benchmarkId=${id}&interpolationType=${interpolationType}&interpolationPoints=${points}`,
    dataFetcher
  );
}

export function useEnvironmentMetadata(data) {
  return useSwr(`${apiUrl}/api/v2/environment/metadata/${data}`, dataFetcher);
}

export function useCount(name) {
  return useSwr(`${apiUrl}/api/v2/${name}/count`, dataFetcher);
}

export function useOsVersionFilter(osName) {
  return useSwr(
    `${apiUrl}/api/v2/environment/metadata/osVersion/forOs?osName=${osName}`,
    dataFetcher
  );
}
// SWR documentation: https://swr.vercel.app/docs/getting-started
