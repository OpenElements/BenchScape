import { Fragment } from "react";
import { Menu, Transition } from "@headlessui/react";
import { CaretDown, List } from "@phosphor-icons/react";
import { availableLanguages } from "../../i18n";
import logo from "../../assets/logo.svg";
import { Link, useLocation } from "react-router-dom";
import { exportBenchmarksCsv } from "../../api";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const AppBar = ({
  label,
  menuNavigations,
  handleChangeLanguage,
  setSidebarOpen,
}) => {
  const { pathname } = useLocation();
  console.log(pathname);

  const heading = pathname === "/benchmarks" ? "Benchmarks" : "Environments";
  const showActions = pathname === "/benchmarks" ? true : false;
  return (
    <div>
      <div className="xl:hidden flex items-center justify-between gap-5 2xl:px-8 2xl:py-7 px-5 py-4 bg-primary-navy">
        <Link to="/">
          <img src={logo} className="h-12 object-contain" alt="Logo" />
        </Link>
        <button
          type="button"
          className="-m-2.5 p-2.5 text-white hover:text-primary-green transition-colors ease-in-out duration-150"
          onClick={() => setSidebarOpen((prev) => !prev)}
        >
          <span className="sr-only">Open sidebar</span>
          <List className="h-7 w-7 fill-current" aria-hidden="true" />
        </button>
      </div>
      <div className="flex items-center bg-winkle 2xl:px-8 2xl:py-7 px-5 py-4 w-full">
        <div className="flex-1 flex flex-col items-center gap-5">
          <div className="flex md:flex-row flex-col md:items-center md:justify-between gap-5 text-primary-navy w-full">
            <div className="space-y-0.5">
              <p className=" text-[22px] font-semibold">{heading}</p>
            </div>
            {showActions && (
              <div className="flex items-center gap-2 text-sm">
                <button className="bg-white rounded-sm text-center px-4 py-1.5 border border-gray-300 hover:bg-gray-100 transition-colors ease-in-out duration-150">
                  TableView
                </button>
                <button
                  onClick={exportBenchmarksCsv}
                  className="bg-white rounded-sm text-center px-4 py-1.5 border border-gray-300 hover:bg-gray-100 transition-colors ease-in-out duration-150"
                >
                  Export
                </button>
                <button className="bg-white rounded-sm text-center px-4 py-1.5 border border-gray-300 hover:bg-gray-100 transition-colors ease-in-out duration-150">
                  Settings
                </button>
              </div>
            )}
          </div>
          <div className="hidden">
            <div className="w-0.5 h-4 bg-primary-navy/10 lg:block hidden"></div>
            <Menu as="div" className="relative">
              <Menu.Button className="-m-1.5 flex items-center p-1.5">
                <span className="hidden lg:flex lg:items-center text-primary-navy transition-colors ease-in-out duration-150">
                  <span className="text-sm leading-6 " aria-hidden="true">
                    {label}
                  </span>
                  <CaretDown
                    className="ml-2 h-3 w-3 "
                    weight="fill"
                    aria-hidden="true"
                  />
                </span>
              </Menu.Button>
              <Transition
                as={Fragment}
                enter="transition ease-out duration-100"
                enterFrom="transform opacity-0 scale-95"
                enterTo="transform opacity-100 scale-100"
                leave="transition ease-in duration-75"
                leaveFrom="transform opacity-100 scale-100"
                leaveTo="transform opacity-0 scale-95"
              >
                <Menu.Items className="absolute left-0 z-10 mt-3 w-48 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none">
                  {menuNavigations.map((item) => (
                    <Menu.Item key={item.name}>
                      {({ active }) => (
                        <a
                          href={item.href}
                          className={classNames(
                            active ? "bg-gray-50" : "",
                            "block px-4 py-2 text-sm leading-6 text-gray-900"
                          )}
                        >
                          {item.name}
                        </a>
                      )}
                    </Menu.Item>
                  ))}
                </Menu.Items>
              </Transition>
            </Menu>
            <div className="w-0.5 h-4 bg-primary-navy/10 lg:block hidden"></div>
            <Menu as="div" className="relative">
              <Menu.Button className="-m-1.5 flex items-center p-1.5">
                <span className="hidden lg:flex lg:items-center text-primary-navy transition-colors ease-in-out duration-150">
                  <span className="text-sm leading-6 " aria-hidden="true">
                    Select Langauge
                  </span>
                  <CaretDown
                    className="ml-2 h-3 w-3 "
                    weight="fill"
                    aria-hidden="true"
                  />
                </span>
              </Menu.Button>
              <Transition
                as={Fragment}
                enter="transition ease-out duration-100"
                enterFrom="transform opacity-0 scale-95"
                enterTo="transform opacity-100 scale-100"
                leave="transition ease-in duration-75"
                leaveFrom="transform opacity-100 scale-100"
                leaveTo="transform opacity-0 scale-95"
              >
                <Menu.Items className="absolute left-0 z-10 mt-3 w-48 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none">
                  {availableLanguages.map((lang) => (
                    <Menu.Item key={lang}>
                      {({ active }) => (
                        <div
                          onClick={() => handleChangeLanguage(lang)}
                          className={classNames(
                            active ? "bg-gray-50" : "",
                            "block px-4 py-2 text-sm leading-6 text-gray-900"
                          )}
                        >
                          {lang}
                        </div>
                      )}
                    </Menu.Item>
                  ))}
                </Menu.Items>
              </Transition>
            </Menu>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AppBar;
