import { useEffect } from "react";
import { createAppBarConfig } from "../utils";

const SettingsPage = () => {
  useEffect(() => {
    createAppBarConfig({
      title: "Settings",
      actions: [],
    });
  }, []);

  return (
    <div>
      <div className="flex items-center bg-alice-blue 2xl:px-8 2xl:py-7 px-5 py-4 w-full"></div>
    </div>
  );
};

export default SettingsPage;
