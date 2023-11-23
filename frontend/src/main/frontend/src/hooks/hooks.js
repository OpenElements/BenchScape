import useSwr from "swr";
import { dataFetcher } from "../api/api";

const apiUrl = process.env.REACT_APP_API_URL;

export function useBenchMarks() {
  return useSwr(`${apiUrl}/api/v2/benchmark/all`, dataFetcher);
}

export function useEnvironments() {
  return useSwr(`${apiUrl}/api/v2/environment/all`, dataFetcher);
}

export function useMeasurements(id) {
  return useSwr(
    `${apiUrl}/api/v2/measurement/find?benchmarkId=${id}`,
    dataFetcher
  );
}

// SWR documentation: https://swr.vercel.app/docs/getting-started
