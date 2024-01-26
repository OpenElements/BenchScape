import useSwr from "swr";
import { dataFetcher } from "../api";

const apiUrl = process.env.REACT_APP_API_URL;

export function useBenchMarks() {
  return useSwr(`${apiUrl}/api/v2/benchmark/all`, dataFetcher);
}

export function useEnvironments() {
  return useSwr(`${apiUrl}/api/v2/environment/all`, dataFetcher);
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

// SWR documentation: https://swr.vercel.app/docs/getting-started
