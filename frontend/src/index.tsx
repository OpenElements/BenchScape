import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { I18nextProvider } from "react-i18next";
import { SWRConfig } from "swr/_internal";
import i18n from "./i18n";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <SWRConfig>
        <I18nextProvider i18n={i18n}>
          <App />
        </I18nextProvider>
      </SWRConfig>
    </BrowserRouter>
  </React.StrictMode>
);
