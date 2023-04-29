import React from "react";
import { ImSpinner8 } from "react-icons/im";

const LoadingTemp = ({ color = "red" }) => {
  return (
    <div className=" bg-gradientBg bg-no-repeat bg-cover flex flex-col justify-center items-center">
      <div className="flex justify-center items-center ">
        <ImSpinner8 className={`text-3xl animate-spin text-white`} />
      </div>
    </div>
  );
};
export default LoadingTemp;
