import { Fragment, useState } from "react";
import { Menu, Transition } from "@headlessui/react";
import user from "../assets/images/user.jpg";
import { List, House, SignOut, CaretDown } from "@phosphor-icons/react";
import { Link } from "react-router-dom";

const menuNavigation = [
  { name: "Menu Item 1", href: "#" },
  { name: "Menu Item 2", href: "#" },
];

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const TopNav = () => {
  const [setSidebarOpen] = useState(false);
  return (
    <div className="sticky top-0 z-40 px-4 sm:px-6 xl:px-7 py-5 w-full">
      <div className="flex items-center h-14 bg-white rounded-lg shadow-lg sm:gap-x-6 gap-x-4 px-5">
        <div className="xl:hidden flex items-center  xl:gap-x-6 gap-x-5">
          <button
            type="button"
            className="-m-2.5 p-2.5 text-gray-500 hover:text-primary-navy transition-colors ease-in-out duration-150"
            onClick={() => setSidebarOpen(true)}
          >
            <span className="sr-only">Open sidebar</span>
            <List className="h-6 w-6 fill-current" aria-hidden="true" />
          </button>
          <div
            className="w-0.5 h-4 bg-primary-navy/10 lg:block hidden"
            aria-hidden="true"
          ></div>
        </div>
        <div className="flex flex-1 gap-x-4 self-stretch justify-end xl:gap-x-6">
          <div className="flex-1 lg:flex hidden items-center gap-5">
            <div className="h-full ">
              <Link
                to="/"
                className="xl:w-12 w-10 h-full flex items-center justify-center lg:text-primary-navy border-b-2 border-highlight-blue"
              >
                <House className="w-5 h-5 -mb-1" weight="bold" />
              </Link>
            </div>
            <div className="w-0.5 h-4 bg-primary-navy/10 lg:block hidden"></div>

            <Menu as="div" className="relative">
              <Menu.Button className="-m-1.5 flex items-center p-1.5">
                <span className="hidden lg:flex lg:items-center text-primary-navy transition-colors ease-in-out duration-150">
                  <span className="text-sm leading-6 " aria-hidden="true">
                    Menu Button
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
                  {menuNavigation.map((item) => (
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
                    Menu Button
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
                  {menuNavigation.map((item) => (
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
                    Menu Button
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
                  {menuNavigation.map((item) => (
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
          </div>

          <div className=" flex items-center justify-end gap-5">
            <Link className="flex items-center gap-3">
              <div className="w-8 h-8 shrink-0 border-2 border-highlight-blue rounded-full overflow-hidden">
                <img src={user} alt="User" className="w-full h-full" />
              </div>
              <p className="text-primary-navy text-sm">Margo Doe</p>
            </Link>

            <div
              className="w-0.5 h-4 bg-primary-navy/10"
              aria-hidden="true"
            ></div>
            <button className="text-primary-navy hover:text-red-600 transition-colors ease-in-out duration-150">
              <span className="sr-only">Sign out</span>
              <SignOut className="w-5 h-5" weight="bold" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TopNav;
