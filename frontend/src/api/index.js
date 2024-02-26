import axios from "axios";

const apiUrl = process.env.REACT_APP_API_URL;

export async function dataFetcher(url) {
  return await axios
    .get(url)
    .then((res) => res.data)
    .catch((error) => console.error(error));
}

export async function postData(url, payload) {
  return await axios
    .post(url, payload)
    .then((res) => res.data)
    .catch((error) => console.error(error));
}

export async function deleteData(url) {
  return await axios.delete(url).catch((error) => console.error(error));
}

export async function exportMeasurementsCsv(benchmarkId) {
  return axios({
    url: `${apiUrl}/api/v2/export/measurements/csv?benchmarkId=${benchmarkId}`,
    method: "GET",
    responseType: "blob",
  }).then((response) => {
    const href = URL.createObjectURL(response.data);

    const link = document.createElement("a");
    link.href = href;
    link.setAttribute("download", "benchmarks.csv");
    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
    URL.revokeObjectURL(href);
  });
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
