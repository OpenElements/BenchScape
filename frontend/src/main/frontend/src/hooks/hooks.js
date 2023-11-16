import useSwr from "swr";
import {dataFetcher} from "../api/api";

const apiUrl = process.env.REACT_APP_API_URL;

export function useBenchMarks() {
  return useSwr(`${apiUrl}/api/benchmark`, dataFetcher);
}

export function useTimeSeries(id) {
  return useSwr(`${apiUrl}/api/timeseries/${id}`, dataFetcher);
}

// SWR documentation: https://swr.vercel.app/docs/getting-started
