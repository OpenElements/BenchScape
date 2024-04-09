import axios from "axios";
import { apiUrl } from "../utils/constants";
import { Environment, Measurement } from "../types";

async function exportCsv(url: string, filename: string) {
  try {
    const response = await axios({
      url,
      method: "GET",
      responseType: "blob",
    });
    const href = URL.createObjectURL(response.data);

    const link = document.createElement("a");
    link.href = href;
    link.setAttribute("download", filename);
    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
    URL.revokeObjectURL(href);
  } catch (error) {
    throw new Error(error);
  }
}

export async function dataFetcher<T>(url: string) {
  return await axios
    .get<T>(url)
    .then((res) => res.data)
    .catch((error) => {
      throw new Error(error);
    });
}

export async function postData<T>(url: string, payload: any) {
  return await axios
    .post<T>(url, payload)
    .then((res) => res.data)
    .catch((error) => {
      throw new Error(error);
    });
}

export async function deleteData(url: string) {
  return await axios.delete(url).catch((error) => {
    throw new Error(error);
  });
}

export async function exportMeasurementsCsv(benchmarkId: string) {
  const url = `${apiUrl}/api/v2/export/measurements/csv?benchmarkId=${benchmarkId}`;
  await exportCsv(url, "measurements.csv");
}

export async function exportEnvironmentsCsv() {
  const url = `${apiUrl}/api/v2/export/environments/csv`;
  await exportCsv(url, "environments.csv");
}

export async function exportBenchmarksCsv() {
  const url = `${apiUrl}/api/v2/export/benchmarks/csv`;
  await exportCsv(url, "benchmarks.csv");
}

export async function getMeasurementsForBechmark(benchmarkId: string) {
  const url = `${apiUrl}/api/v2/measurement/find?benchmarkId=${benchmarkId}`;
  const data = await dataFetcher<Array<Measurement>>(url);
  return data?.length;
}

export async function saveEnvironment(payload: Object) {
  return await postData<Array<Environment>>(
    `${apiUrl}/api/v2/environment`,
    payload
  );
}

export async function deleteEnvironment(id: string) {
  return await deleteData(`${apiUrl}/api/v2/environment?id=${id}`);
}
