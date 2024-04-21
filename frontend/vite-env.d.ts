/// <reference types="vite/client" />

declare global {
  interface Window {
    appEnv?: appEnv;
    stores?: Record<string, any>;
  }
}

export type appEnv = "production" | "development";
