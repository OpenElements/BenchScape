import axios from "axios";
import { apiUrl } from "../utils/constants";

// Common function for exporting CSV
async function exportCsv(url, filename) {
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

export async function dataFetcher(url) {
  return axios
    .get(url)
    .then((res) => res.data)
    .catch((error) => {
      throw new Error(error);
    });
}

export async function postData(url, payload) {
  return await axios
    .post(url, payload)
    .then((res) => res.data)
    .catch((error) => {
      throw new Error(error);
    });
}

export async function deleteData(url) {
  return await axios.delete(url).catch((error) => {
    throw new Error(error);
  });
}

export async function exportMeasurementsCsv(benchmarkId) {
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

export async function getMeasurementsForBechmark(benchmarkId) {
  const url = `${apiUrl}/api/v2/measurement/find?benchmarkId=${benchmarkId}`;
  const data = await dataFetcher(url);
  return data?.length;
}

export async function saveEnvironment(payload) {
  return await postData(`${apiUrl}/api/v2/environment`, payload);
}

export async function deleteEnvironment(id) {
  return await deleteData(`${apiUrl}/api/v2/environment?id=${id}`);
}
