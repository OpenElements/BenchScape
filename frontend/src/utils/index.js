export function dataSlicer(data, itemsPerPage) {
  return Math.ceil(data?.length / itemsPerPage);
}
