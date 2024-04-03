import resolveConfig from "tailwindcss/resolveConfig";

export function dataSlicer(data, itemsPerPage) {
  return Math.ceil(data?.length / itemsPerPage);
}

export function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export function tailwindConfig() {
  return resolveConfig("../../tailwind.config.js");
}

export function getBreakpointValue(value) {
  const breakpoints = tailwindConfig().theme.screens;
  return +breakpoints[value].slice(0, breakpoints[value].indexOf("px"));
}

export function getCurrentBreakpoint() {
  const breakpoints = tailwindConfig().theme.screens;
  let currentBreakpoint;
  let biggestBreakpointValue = 0;
  for (const breakpoint of Object.keys(breakpoints)) {
    const breakpointValue = getBreakpointValue(breakpoint);
    if (
      breakpointValue > biggestBreakpointValue &&
      window.innerWidth >= breakpointValue
    ) {
      biggestBreakpointValue = breakpointValue;
      currentBreakpoint = breakpoint;
    }
  }
  return { currentBreakpoint, currentBreakpointValue: biggestBreakpointValue };
}

export function createAppBarConfig(appBarConfig) {
  return document.dispatchEvent(
    new CustomEvent("appBarConfig", { detail: appBarConfig }),
  );
}
