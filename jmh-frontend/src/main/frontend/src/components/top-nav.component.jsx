import user from "../assets/images/user.jpg";
import { CaretDown, House, SignOut } from "@phosphor-icons/react";
import { Link } from "react-router-dom";

const TopNav = () => {
  return (
    <div class="flex flex-1 gap-x-4 self-stretch lg:gap-x-6">
      <div className="flex-1 flex items-center px-8">
        <div className="flex items-center gap-5 h-full">
          <Link
            to="/"
            className="w-12 h-full flex items-center justify-center border-b-2 border-highlight-blue"
          >
            <House className="w-5 h-5" weight="bold" />
          </Link>
          <div className="w-0.5 h-4 bg-primary-navy/10"></div>
          <div>
            <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
              Menu Button
              <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
            </button>
          </div>
          <div className="w-0.5 h-4 bg-primary-navy/10"></div>
          <div>
            <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
              Menu Button
              <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
            </button>
          </div>
          <div className="w-0.5 h-4 bg-primary-navy/10"></div>
          <div>
            <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
              Menu Button
              <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
            </button>
          </div>
          <div className="w-0.5 h-4 bg-primary-navy/10"></div>
          <div>
            <button className="inline-flex items-center gap-2 text-primary-navy text-sm">
              Menu Button
              <CaretDown className="w-3 h-3 text-primary-navy" weight="fill" />
            </button>
          </div>
        </div>
      </div>
      <div class="flex items-center gap-5 px-8">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 border-2 border-highlight-blue rounded-full overflow-hidden">
            <img src={user} alt="User" className="w-full h-full" />
          </div>
          <p className="text-primary-navy text-sm">Margo Doe</p>
        </div>
        <div className="w-0.5 h-4 bg-primary-navy/10"></div>
        <button className="text-primary-navy hover:text-red-600 transition-colors ease-in-out duration-150">
          <SignOut className="w-5 h-5" weight="bold" />
        </button>
      </div>
    </div>
  );
};

export default TopNav;
