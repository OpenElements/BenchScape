import useSwr from "swr";
import { apiUrl } from "../utils/constants";
import { getCurrentBreakpoint } from "../utils";
import { dataFetcher } from "../api";
import { useEffect, useState } from "react";
import {
  BenchmarksResponse,
  Environment,
  EnvironmentsResponse,
  MeasurementsResponse,
} from "../types";

/*********************** Resources  ***************************/
/**
 *  SWR documentation: https://swr.vercel.app/docs/getting-started
 */

export function useBenchMarks() {
  return useSwr<BenchmarksResponse, Error>(
    `${apiUrl}/api/v2/benchmark/all`,
    dataFetcher
  );
}

export function useEnvironments(queries?: Object) {
  let params = "";

  if (queries !== undefined) {
    const withValues = Object.entries(queries)
      .filter(([_key, value]) => Boolean(value))
      .map(([key, value]) => [key, value]);

    params = new URLSearchParams(withValues).toString();
  }

  return useSwr<EnvironmentsResponse, Error>(
    `${apiUrl}/api/v2/environment/findByQuery?${params}`,
    dataFetcher
  );
}

export function useEnvironmentById(id: string) {
  return useSwr<Environment, Error>(
    `${apiUrl}/api/v2/environment/find?id=${id}`,
    dataFetcher
  );
}

export function useMeasurements(filters: Object) {
  let params = "";

  if (filters !== undefined) {
    const withValues = Object.entries(filters)
      .filter(([_key, value]) => Boolean(value))
      .map(([key, value]) => [key, value]);

    params = new URLSearchParams(withValues).toString();
  }
  return useSwr<MeasurementsResponse, Error>(
    `${apiUrl}/api/v2/measurement/find?${params}`,
    dataFetcher
  );
}

export function useMeasurementsSmooth(id: string) {
  return useSwr<MeasurementsResponse, Error>(
    `${apiUrl}/api/v2/measurement/find?benchmarkId=${id}&smooth=true`,
    dataFetcher
  );
}

export function useMeasurementsInterpolated(
  id: string,
  interpolationType: string,
  points: any
) {
  return useSwr<MeasurementsResponse, Error>(
    `${apiUrl}/api/v2/measurement/findInterpolated?benchmarkId=${id}&interpolationType=${interpolationType}&interpolationPoints=${points}`,
    dataFetcher
  );
}

export function useEnvironmentMetadata(meta: string) {
  return useSwr<Array<string>, Error>(
    `${apiUrl}/api/v2/environment/metadata/${meta}`,
    dataFetcher
  );
}

export function useCount(name: string) {
  return useSwr<number, Error>(`${apiUrl}/api/v2/${name}/count`, dataFetcher);
}

export function useOS() {
  return useSwr<Array<string>, Error>(
    `${apiUrl}/api/v2/environment/metadata/os`,
    dataFetcher
  );
}

export function useOSFamily() {
  return useSwr<Array<string>, Error>(
    `${apiUrl}/api/v2/environment/metadata/osFamily`,
    dataFetcher
  );
}

export function useOsVersionFilter(osName: string) {
  return useSwr(
    `${apiUrl}/api/v2/environment/metadata/osVersion/forOs?osName=${osName}`,
    dataFetcher
  );
}

export function useForOsFamilyFilter(osFamily: string) {
  return useSwr<Array<string>, Error>(
    `${apiUrl}/api/v2/environment/metadata/osVersion/forOsFamily?osFamily=${osFamily}`,
    dataFetcher
  );
}

export function useBreakpoint(breakpointKey: string) {
  const [bool, setBool] = useState(false);

  useEffect(() => {
    function handleResize() {
      const { currentBreakpoint } = getCurrentBreakpoint();
      setBool(currentBreakpoint === breakpointKey);
    }

    handleResize();

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, [breakpointKey]);

  const capitalizedKey =
    breakpointKey[0].toUpperCase() + breakpointKey.substring(1);
  return {
    [`is${capitalizedKey}`]: bool,
  };
}
