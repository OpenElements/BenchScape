import { List } from "@phosphor-icons/react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import SideNav from "./SideNav";
import logo from "../assets/logo.svg";

const AppBar = () => {
  const [sideBarOpen, setSideBarOpen] = useState(false);
  const [{ actions, title }, setAppBarConfig] = useState({
    title: "",
    actions: [],
  });

  // Custom event to listen to changes in the appBar configurations
  // This only runs on initial page load. If more retriggering is needed later,
  // dependecies should be added accordingly.
  useEffect(() => {
    const listener = (event) => {
      setAppBarConfig(event.detail);
    };
    document.addEventListener("appBarConfig", listener);

    return () => {
      document.removeEventListener("appBarConfig", listener);
    };
  }, []);

  return (
    <div>
      <div className="xl:hidden flex items-center justify-between gap-5 2xl:px-8 2xl:py-7 px-5 py-4 bg-primary-navy">
        <Link to="/">
          <img src={logo} className="h-12 object-contain" alt="Logo" />
        </Link>
        <button
          type="button"
          className="-m-2.5 p-2.5 text-white hover:text-primary-green transition-colors ease-in-out duration-150"
          onClick={() => setSideBarOpen((prev) => !prev)}
        >
          <span className="sr-only">Open sidebar</span>
          <List className="h-7 w-7 fill-current" aria-hidden="true" />
        </button>
      </div>
      <div className="xl:fixed w-full xl:pl-64 xl:pt-4 z-50">
        <div className="xl:px-4">
          <div className="flex items-center bg-winkle 2xl:px-8 2xl:py-7 px-5 py-4 w-full">
            <div className="xl:pl-4 flex-1 flex flex-col items-center gap-5">
              <div className="flex md:flex-row flex-col md:items-center md:justify-between gap-5 text-primary-navy w-full">
                <div className="space-y-0.5">
                  <p className="text-[22px] font-semibold">{title}</p>
                </div>
                {Array.isArray(actions) && (
                  <div className="flex items-center gap-2 text-sm">
                    {actions.map(({ name, action, disabled }, index) => (
                      <button
                        key={index}
                        onClick={action}
                        disabled={disabled}
                        className="bg-white rounded-sm text-center px-4 py-1.5 border border-gray-300 hover:bg-gray-100 transition-colors ease-in-out duration-150"
                      >
                        {name}
                      </button>
                    ))}
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      <SideNav sidebarOpen={sideBarOpen} setSideBarOpen={setSideBarOpen} />
    </div>
  );
};

export default AppBar;
