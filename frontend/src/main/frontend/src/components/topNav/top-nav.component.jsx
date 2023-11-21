import AppBar from "../appBar/app-bar.component";
import { useTranslation } from "react-i18next";

const TopNav = () => {
  const {
    i18n: { changeLanguage },
  } = useTranslation();
  const menuNavigations = [
    { name: "New item", href: "#" },
    { name: "New item", href: "#" },
  ];

  const handleChangeLanguage = (newLanguage) => {
    changeLanguage(newLanguage);
  };
  return (
    <AppBar
      label="Menu button"
      menuNavigations={menuNavigations}
      handleChangeLanguage={handleChangeLanguage}
    />
  );
};

export default TopNav;
