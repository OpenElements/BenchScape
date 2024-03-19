import { Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Dialog, Transition } from "@headlessui/react";
import { CirclesThree, Wrench, Gear, Globe } from "@phosphor-icons/react";
import { Link, useLocation } from "react-router-dom";
import { useBreakpoint, useCount } from "../hooks";
import { availableLanguages } from "../i18n";
import { Menu } from "@headlessui/react";
import { classNames } from "../utils";
import logo from "../assets/logo.svg";

const SideNav = ({ setSideBarOpen, sidebarOpen }) => {
  const {
    t,
    i18n: { changeLanguage },
  } = useTranslation();
  const { pathname } = useLocation();
  const { data: benchmarks } = useCount("benchmark");
  const { data: environments } = useCount("environment");

  const navigation = [
    {
      name: t("Benchmarks"),
      href: "/benchmarks",
      icon: CirclesThree,
      count: benchmarks ? benchmarks : 0,
    },
    {
      name: t("Environments"),
      href: "/environments",
      icon: Wrench,
      count: environments ? environments : 0,
    },
  ];

  return (
    <NavWrapper setSideBarOpen={setSideBarOpen} sidebarOpen={sidebarOpen}>
      <div className="fixed inset-y-0 z-50 flex w-64 flex-col">
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
                          pathname.includes(item.href)
                            ? "bg-primary-purple text-white"
                            : "text-indigo-200 hover:text-white hover:bg-indigo-900/40",
                          "sidebar-nav-link"
                        )}
                      >
                        <item.icon
                          className={classNames(
                            pathname.includes(item.href)
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
                      to="/settings"
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
    </NavWrapper>
  );
};

export default SideNav;

function NavWrapper({ setSideBarOpen, sidebarOpen, children }) {
  const { is2xl } = useBreakpoint("2xl");
  const { isXl } = useBreakpoint("xl");

  return isXl || is2xl ? (
    children
  ) : (
    <Transition.Root show={sidebarOpen} as={Fragment}>
      <Dialog
        as="div"
        className="fixed inset-0 overflow-hidden z-50"
        open={sidebarOpen}
        onClose={() => setSideBarOpen(false)}
      >
        {/* Background Overlay */}
        <Transition.Child
          as={Fragment}
          enter="transition-opacity ease-linear duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="transition-opacity ease-linear duration-300"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <Dialog.Overlay className="fixed inset-0 bg-gray-950/80" />
        </Transition.Child>

        {/* Sidebar Content */}
        <Transition.Child
          as={Fragment}
          enter="transition ease-in-out duration-300 transform"
          enterFrom="-translate-x-full"
          enterTo="translate-x-0"
          leave="transition ease-in-out duration-300 transform"
          leaveFrom="translate-x-0"
          leaveTo="-translate-x-full"
        >
          <div className="fixed inset-y-0 left-0 w-64 bg-white shadow-md">
            {children}
          </div>
        </Transition.Child>
      </Dialog>
    </Transition.Root>
  );
}
