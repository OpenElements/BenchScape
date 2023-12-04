import { Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Dialog, Transition } from "@headlessui/react";
import { CirclesThree, Wrench, Gear, Globe } from "@phosphor-icons/react";
import { Link, useLocation } from "react-router-dom";
import { useBenchMarks } from "../../hooks/hooks";
import { availableLanguages } from "../../i18n";
import { Menu } from "@headlessui/react";
import logo from "../../assets/logo.svg";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const SideNav = ({ setSidebarOpen, sidebarOpen }) => {
  const {
    t,
    i18n: { changeLanguage },
  } = useTranslation();
  const { pathname } = useLocation();
  const { data } = useBenchMarks();

  const navigation = [
    {
      name: t("Benchmarks"),
      href: "/benchmarks",
      icon: CirclesThree,
      count: data ? data.length : 0,
    },
    {
      name: t("Environments"),
      href: "/environment",
      icon: Wrench,
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
          onClose={() => setSidebarOpen(false)}
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
              <Dialog.Panel className="relative w-64 h-full flex">
                {/* Sidebar component, swap this element with another sidebar if you like */}
                <div className="flex grow flex-col overflow-y-auto bg-primary-navy pb-4">
                  <div className="flex shrink-0 py-5 items-center justify-between px-5 gap-4 relative w-full">
                    <Link to="/">
                      <img src={logo} className="w-full" alt="Logo" />
                    </Link>
                    {/* <button
                        type="button"
                        className="p-px bg-red-500 rounded-sm text-white hover:bg-red-600 transition-colors ease-in-out duration-150"
                        onClick={() => setSidebarOpen(false)}
                      >
                        <span className="sr-only">Close sidebar</span>
                        <X className="h-5 w-5 shrink-0" aria-hidden="true" />
                      </button> */}
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
                                    : "text-indigo-200 hover:text-white hover:bg-indigo-900/40",
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

                      <ul className="mt-auto">
                        <li>
                          <Link
                            to="#"
                            className="sidebar-nav-link hover:bg-indigo-900/40"
                          >
                            <span className="flex items-center gap-3">
                              <Gear className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white" />
                              {t("Settings")}
                            </span>
                          </Link>
                        </li>
                        <li>
                          <Menu as="div" className="relative w-full">
                            <Menu.Button className="w-full ">
                              <span className="sidebar-nav-link hover:bg-indigo-900/40">
                                <Globe
                                  className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white"
                                  aria-hidden="true"
                                />
                                <span
                                  className="text-sm leading-6 "
                                  aria-hidden="true"
                                >
                                  Select Langauge
                                </span>
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
                              <Menu.Items className="absolute bottom-14 z-10 w-full origin-top-right bg-black/30 backdrop-blur-sm py-4 shadow-lg ring-1 ring-gray-900/5 focus:outline-none">
                                {availableLanguages.map((lang) => (
                                  <Menu.Item key={lang}>
                                    {({ active }) => (
                                      <div
                                        onClick={() => changeLanguage(lang)}
                                        className={classNames(
                                          active ? "" : "",
                                          "block px-8 py-2 text-sm leading-6 text-white hover:bg-white/5"
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
                        </li>
                      </ul>
                    </ul>
                  </nav>
                </div>
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </Dialog>
      </Transition.Root>

      {/* DesktopSideNavbar */}
      <div className="hidden xl:fixed xl:inset-y-0 xl:z-50 xl:flex xl:w-64 xl:flex-col">
        <div className="flex grow flex-col overflow-y-auto bg-primary-navy pb-4">
          <div className="flex py-5 shrink-0 items-center justify-center border-b border-white/10 px-6">
            <Link to="/">
              <img src={logo} className="w-full  object-contain" alt="Logo" />
            </Link>
          </div>
          <nav className="flex flex-1 flex-col px-6">
            <ul className="flex flex-1 flex-col justify-between gap-y-7">
              <li className="">
                <ul className="-mx-6">
                  {navigation.map((item) => (
                    <li key={item.name}>
                      <Link
                        to={item.href}
                        className={classNames(
                          pathname === item.href
                            ? "bg-primary-purple text-white"
                            : "text-indigo-200 hover:text-white hover:bg-indigo-900/40",
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
              <li>
                <ul className="mt-auto -mx-6">
                  <li>
                    <Link
                      to="#"
                      className="sidebar-nav-link hover:bg-indigo-900/40"
                    >
                      <span className="flex items-center gap-3">
                        <Gear className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white" />
                        {t("Settings")}
                      </span>
                    </Link>
                  </li>
                  <li>
                    <Menu as="div" className="relative w-full">
                      <Menu.Button className="w-full ">
                        <span className="sidebar-nav-link hover:bg-indigo-900/40">
                          <Globe
                            className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white"
                            aria-hidden="true"
                          />
                          <span
                            className="text-sm leading-6 "
                            aria-hidden="true"
                          >
                            Select Langauge
                          </span>
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
                        <Menu.Items className="absolute bottom-14 z-10 w-full origin-top-right bg-black/30 backdrop-blur-sm py-4 shadow-lg ring-1 ring-gray-900/5 focus:outline-none">
                          {availableLanguages.map((lang) => (
                            <Menu.Item key={lang}>
                              {({ active }) => (
                                <div
                                  onClick={() => changeLanguage(lang)}
                                  className={classNames(
                                    active ? "" : "",
                                    "block px-8 py-2 text-sm leading-6 text-white hover:bg-white/5"
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
                  </li>
                </ul>
              </li>
            </ul>
          </nav>
        </div>
      </div>
      {/* <TopNav /> */}
    </>
  );
};

export default SideNav;
