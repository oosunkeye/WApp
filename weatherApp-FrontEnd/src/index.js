import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App/App";
import { BrowserRouter as Router } from "react-router-dom";
import AppContextProvider from "./Context/applicationContext";
import { Toaster } from "react-hot-toast";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <AppContextProvider>
    <Router>
      <App />
    </Router>
    <Toaster
      toastOptions={{
        duration: 50000,
        style: {
          background: "#7e0bdb",
          color: "#fff",
        },
      }}
    />
  </AppContextProvider>
);
