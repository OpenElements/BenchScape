import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { SWRConfig } from "swr/_internal";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <SWRConfig>
        <App />
      </SWRConfig>
    </BrowserRouter>
  </React.StrictMode>
);
