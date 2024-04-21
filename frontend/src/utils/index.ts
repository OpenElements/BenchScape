import { Config } from "tailwindcss";
import resolveConfig from "tailwindcss/resolveConfig";

import localConfig from "../../tailwind.config.js";

export * from "./constants";
export * from "./state";

export function dataSlicer(data: Array<Object>, itemsPerPage: number) {
  return Math.ceil(data?.length / itemsPerPage);
}

export function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export function tailwindConfig() {
  return resolveConfig(localConfig as Config);
}

export function getBreakpointValue(value: string) {
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
    new CustomEvent("appBarConfig", { detail: appBarConfig })
  );
}
