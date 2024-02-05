export function dataSlicer(data, itemsPerPage) {
  return Math.ceil(data?.length / itemsPerPage);
}

export function getUuidFromUrl(pathname) {
  const uuidPattern =
    /[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}/;
  const uuidMatch = pathname.match(uuidPattern);
  return uuidMatch ? uuidMatch[0] : null;
}
