import logo from "../assets/logo.svg";
import {
  ArrowsClockwise,
  Bell,
  ChartLine,
  Gear,
  Star,
} from "@phosphor-icons/react";
import { Link } from "react-router-dom";

const SideNav = () => {
  return (
    <div class="hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-72 lg:flex-col">
      <div class="flex grow flex-col gap-y-5 overflow-y-auto bg-primary-navy pb-4 px-8">
        <div class="flex h-20 shrink-0 items-center justify-start">
          <img src={logo} className="h-10" alt="Logo" />
        </div>
        <nav class="flex flex-1 flex-col">
          <ul class="flex flex-1 flex-col gap-y-7">
            <li>
              <ul class="-mx-8 space-y-1">
                <li>
                  <Link to="/regular-updates" className="sidebar-nav-link">
                    <span class="flex items-center gap-3">
                      <ArrowsClockwise className="w-5 h-5 shrink-0" />
                      Regular Updates
                    </span>
                    <span className="sidebar-badge">3</span>
                  </Link>
                </li>
                <li>
                  <Link to="/favorites" className="sidebar-nav-link">
                    <span class="flex items-center gap-3">
                      <Star className="w-5 h-5 shrink-0" />
                      Favorites
                    </span>
                    <span className="sidebar-badge">12</span>
                  </Link>
                </li>
                <li>
                  <Link to="/analystics" className="sidebar-nav-link">
                    <span class="flex items-center gap-3">
                      <ChartLine className="w-5 h-5 shrink-0" />
                      Analytics
                    </span>
                  </Link>
                </li>
                <li>
                  <Link to="/allerts" className="sidebar-nav-link">
                    <span class="flex items-center gap-3">
                      <Bell className="w-5 h-5 shrink-0" />
                      Alerts
                    </span>
                  </Link>
                </li>
              </ul>
            </li>

            <li class="mt-auto w-full">
              <ul className="-mx-8">
                <li>
                  <Link to="/settings" className="sidebar-nav-link">
                    <span class="flex items-center gap-3">
                      <Gear className="w-5 h-5 shrink-0" />
                      Settings
                    </span>
                  </Link>
                </li>
              </ul>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
};

export default SideNav;
