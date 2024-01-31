import axios from "axios";

const apiUrl = process.env.REACT_APP_API_URL;

export async function dataFetcher(url) {
  return await axios.get(url).then((res) => res.data);
}

export async function exportBenchmarksCsv() {
  return axios({
    url: `${apiUrl}/api/v2/export/benchmarks/csv`,
    method: "GET",
    responseType: "blob",
  }).then((response) => {
    const href = URL.createObjectURL(response.data);

    const link = document.createElement("a");
    link.href = href;
    link.setAttribute("download", "benchmarks.csv"); //or any other extension
    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
    URL.revokeObjectURL(href);
  });
}

export async function getBenchmarkById(benchmarkId) {
  const url = `${apiUrl}/api/v2/measurement/find?benchmarkId=${benchmarkId}`;

  try {
    const data = await dataFetcher(url);
    return data?.length;
  } catch (error) {
    console.error(error);
  }
}
