import React, { useContext, useState } from "react";
import { Link } from "react-router-dom";
import { AppContext } from "../../Context/applicationContext";

const Header = (props) => {
  const appContext = useContext(AppContext);

  const [showMenu, setShowMenu] = useState(false);

  const data = appContext.getSession().userData;

  const hour = new Date().getHours();

  const welcomeTypes = ["Good morning", "Good afternoon", "Good evening"];

  let welcomeText = "";

  if (hour < 12) welcomeText = welcomeTypes[0];
  else if (hour < 18) welcomeText = welcomeTypes[1];
  else welcomeText = welcomeTypes[2];

  const logout = () => {
    appContext.logout();
  };

  return (
    <header className="sticky top-0 z-50">
      <nav className="flex items-center justify-between flex-wrap bg-white/30 p-6 border-b border-indigo-700 shadow-lg">
        <div className="block lg:hidden">
          <button
            className="flex items-center px-3 py-2 border rounded text-cColor-50 border-gray-900 hover:text-black hover:border-white"
            onClick={() => setShowMenu(!showMenu)}
          >
            <svg
              className="fill-current h-3 w-3"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <title>Menu</title>
              <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z" />
            </svg>
          </button>
        </div>
        <div
          className={`w-full ${
            showMenu ? "block" : "hidden"
          } lg:flex lg:items-center lg:w-auto`}
        >
          <div className="flex items-center flex-shrink-0 text-white-50 mr-6">
            <Link
              className="inline-block no-underline text-white hover: text-cColor-50 font-bold text-lg py-2 px-4 lg:-ml-2"
              to="/"
            >
              {welcomeText} {data.firstName + " " + data.lastName},
            </Link>
          </div>
          <div className="text-sm lg:flex-grow">
            <Link
              className="block mt-4 lg:inline-block lg:mt-0 font-bold  text-white hover: text-cColor-50 text-lg py-2 px-4 lg:-ml-2"
              to="/currentWeatherData"
            >
              Live Weather Data
            </Link>
            <Link
              className="block mt-4 lg:inline-block lg:mt-0 font-bold text-white hover: text-cColor-50 text-lg py-2 px-4 lg:-ml-2"
              to="/historyWeatherData"
            >
              History Weather Data
            </Link>
          </div>
          <div>
            <Link to="/login">
              <div
                className="inline-block no-underline text-white hover:text-red-500  font-bold text-lg py-2 px-4 rounded"
                onClick={() => logout()}
              >
                Logout
              </div>
            </Link>
          </div>
          {/* <div>
            <Link to="/login">
              <div
                className="inline-block no-underline text-cColor-50 hover:text-red-500  font-bold text-lg py-2 px-4 rounded"
                onClick={() => logout()}
              >
                Welcome Oladapo Osunkeye
              </div>
            </Link>
          </div> */}
        </div>
      </nav>
      <main className="w-screen relative">{props.children}</main>
    </header>
  );
};

export default Header;
