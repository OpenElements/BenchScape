export const hexToRGB = (h: string) => {
  let r = 0;
  let g = 0;
  let b = 0;
  if (h.length === 4) {
    r = parseInt(`0x${h[1]}${h[1]}`, 16);
    g = parseInt(`0x${h[2]}${h[2]}`, 16);
    b = parseInt(`0x${h[3]}${h[3]}`, 16);
  } else if (h.length === 7) {
    r = parseInt(`0x${h[1]}${h[2]}`, 16);
    g = parseInt(`0x${h[3]}${h[4]}`, 16);
    b = parseInt(`0x${h[5]}${h[6]}`, 16);
  }
  return `${r},${g},${b}`;
};

export const formatValue = (value: number | string) => {
  return typeof value === "number" ? String(Math.round(value)) : value;
};
