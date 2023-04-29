import React from "react";
import { ImSpinner8 } from "react-icons/im";

const LoadingIndicator = ({ color = "red" }) => {
  return (
    <div className="min-h-screen bg-gradientBg bg-no-repeat bg-cover flex flex-col justify-center items-center">
      <div className="flex justify-center items-center h-500">
        <ImSpinner8 className={`text-5xl animate-spin text-${color}`} />
      </div>
    </div>
  );
};
export default LoadingIndicator;
