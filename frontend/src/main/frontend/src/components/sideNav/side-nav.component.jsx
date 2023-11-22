import { Fragment, useState } from "react";
import { useTranslation } from "react-i18next";
import { Dialog, Transition } from "@headlessui/react";
import logo from "../../assets/logo.svg";
import { Bell, Gear, X } from "@phosphor-icons/react";
import { Link, useLocation } from "react-router-dom";
import { useBenchMarks } from "../../hooks/hooks";
import TopNav from "../topNav/top-nav.component";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const SideNav = () => {
  const {
    t,
    //i18n: { changeLanguage },
  } = useTranslation();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const { pathname } = useLocation();
  const { data } = useBenchMarks();

  const navigation = [
    {
      name: t("Benchmarks"),
      href: "/benchmarks",
      icon: Bell,
      count: data ? data.length : 0,
    },
    {
      name: t("Environments"),
      href: "/environment",
      icon: Bell,
      count: 0,
    },
  ];

  return (
    <>
      {/* MobileOffCanvasSideBar */}
      <Transition.Root show={sidebarOpen} as={Fragment}>
        <Dialog
          as="div"
          className="relative z-50 xl:hidden"
          onClose={setSidebarOpen}
        >
          <Transition.Child
            as={Fragment}
            enter="transition-opacity ease-linear duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="transition-opacity ease-linear duration-300"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-gray-950/80" />
          </Transition.Child>

          <div className="fixed inset-0 flex">
            <Transition.Child
              as={Fragment}
              enter="transition ease-in-out duration-300 transform"
              enterFrom="-translate-x-full"
              enterTo="translate-x-0"
              leave="transition ease-in-out duration-300 transform"
              leaveFrom="translate-x-0"
              leaveTo="-translate-x-full"
            >
              <Dialog.Panel className="relative mr-16 flex w-full max-w-xs flex-1">
                {/* Sidebar component, swap this element with another sidebar if you like */}
                <div className="flex grow flex-col gap-y-5 overflow-y-auto bg-primary-navy pb-4">
                  <div className="flex h-20 shrink-0 items-center justify-between px-6 relative">
                    <Link to="/">
                      <img src={logo} className="h-10" alt="Logo" />
                    </Link>

                    <button
                      type="button"
                      className="-m-2.5 p-2.5 text-white hover:text-red-600 transition-colors ease-in-out duration-150"
                      onClick={() => setSidebarOpen(false)}
                    >
                      <span className="sr-only">Close sidebar</span>
                      <X className="h-6 w-6 shrink-0" aria-hidden="true" />
                    </button>
                  </div>
                  <nav className="flex flex-1 flex-col">
                    <ul className="flex flex-1 flex-col gap-y-7">
                      <li>
                        <ul className="">
                          {navigation.map((item) => (
                            <li key={item.name}>
                              <Link
                                to={item.href}
                                className={classNames(
                                  item.current
                                    ? "bg-primary-purple text-white"
                                    : "text-indigo-200 hover:text-white hover:bg-indigo-700",
                                  "sidebar-nav-link"
                                )}
                              >
                                <item.icon
                                  className={classNames(
                                    item.current
                                      ? "text-white"
                                      : "text-indigo-200 group-hover:text-white",
                                    "h-6 w-6 shrink-0"
                                  )}
                                  aria-hidden="true"
                                />
                                {item.name}
                                <span className="sidebar-badge">
                                  {item.count}
                                </span>
                              </Link>
                            </li>
                          ))}
                        </ul>
                      </li>

                      <li className="mt-auto ">
                        <Link
                          to="#"
                          className="sidebar-nav-link hover:bg-indigo-700"
                        >
                          <span className="flex items-center gap-3">
                            <Gear className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white" />
                            {t("Settings")}
                          </span>
                        </Link>
                      </li>
                    </ul>
                  </nav>
                </div>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </Dialog>
      </Transition.Root>

      {/* DesktopSideNavbar */}
      <div className="hidden xl:fixed xl:inset-y-0 xl:z-50 xl:flex xl:w-72 xl:flex-col">
        <div className="flex grow flex-col gap-y-5 overflow-y-auto bg-primary-navy px-8 pb-4">
          <div className="flex h-20 shrink-0 items-center justify-start">
            <Link to="/">
              <img src={logo} className="h-10" alt="Logo" />
            </Link>
          </div>
          <nav className="flex flex-1 flex-col">
            <ul className="flex flex-1 flex-col gap-y-7">
              <li>
                <ul className="-mx-8">
                  {navigation.map((item) => (
                    <li key={item.name}>
                      <Link
                        to={item.href}
                        className={classNames(
                          pathname === item.href
                            ? "bg-primary-purple text-white"
                            : "text-indigo-200 hover:text-white hover:bg-indigo-700",
                          "sidebar-nav-link"
                        )}
                      >
                        <item.icon
                          className={classNames(
                            pathname === item.href
                              ? "text-white"
                              : "text-indigo-200 group-hover:text-white",
                            "h-6 w-6 shrink-0"
                          )}
                          aria-hidden="true"
                        />
                        {item.name}
                        <span className="sidebar-badge">{item.count}</span>
                      </Link>
                    </li>
                  ))}
                </ul>
              </li>

              <li className="mt-auto -mx-8">
                <Link to="#" className="sidebar-nav-link hover:bg-indigo-700">
                  <span className="flex items-center gap-3">
                    <Gear className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white" />
                    {t("Settings")}
                  </span>
                </Link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
      <TopNav />
    </>
  );
};

export default SideNav;
