import axios from "axios";

export async function dataFetcher(url) {
  return await axios.get(url).then((res) => res.data);
}
