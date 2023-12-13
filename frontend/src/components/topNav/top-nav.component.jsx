import AppBar from "../appBar/app-bar.component";
import { useTranslation } from "react-i18next";

const TopNav = ({ setSidebarOpen }) => {
  const {
    i18n: { changeLanguage },
  } = useTranslation();
  const menuNavigations = [
    { name: "New item 1", href: "#" },
    { name: "New item 2", href: "#" },
  ];

  const handleChangeLanguage = (newLanguage) => {
    changeLanguage(newLanguage);
  };
  return (
    <AppBar
      label="Menu button"
      menuNavigations={menuNavigations}
      handleChangeLanguage={handleChangeLanguage}
      setSidebarOpen={setSidebarOpen}
    />
  );
};

export default TopNav;
