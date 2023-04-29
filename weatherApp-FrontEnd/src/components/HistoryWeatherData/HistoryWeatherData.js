import React, { useState, useEffect } from "react";
import { getHistoryWeatherDataApi } from "../../util/ApiUtil";
import DisplayWeatherData from "../CurrentWeatherData/DisplayWeatherData";
import LoadingIndicator from "../LoadingIndicator/LoadingIndicator";
import NoHistoryWeatherPresent from "./NoHistoryWeatherPresent";

import TokenExpirationPage from "../TokenExpirationPage/TokenExpirationPage";

const HistoryWeatherData = ({ currentUser }) => {
  // Result-store weather data history results into an array
  const [results, setResults] = useState([]);
  // tokenExpired: used to track whether or not the user's authentication token has expired
  const [tokenExpired, setTokenExpired] = useState(false);
  // Data: used to track if there's any weather data history available
  const [data, setData] = useState(null);
  // Loading: used to show/hide the loading indicator
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getMyResults();
  }, []);

  const getMyResults = async () => {
    setLoading(true); // Set loading to true before making API call
    const apiResponse = await getHistoryWeatherDataApi(currentUser.token); // Make API call using getHistoryWeatherDataApi function
    console.log(apiResponse);
    if (apiResponse && apiResponse.length > 0) {
      setResults(apiResponse); // Update results variable with data received from server
      setData(true); // Update data variable with data received from server if available
      setLoading(false); // Update data variable to false if no data available
    }
    // If there's no history weather data available, update the state variables and hide the loading indicator
    else if (apiResponse && apiResponse.length === 0) {
      setData(false);
      setLoading(false); // Set loading to false after API call completes
    }

    // If the authentication token has expired, update the state variables and hide the loading indicator
    else if (apiResponse.response.data.httpStatusCode === 401) {
      setTokenExpired(true); // Update tokenExpired variable to true if authentication token has expired
      setLoading(false);
    }
  };

  // Show the loading indicator if the data is being loaded
  if (loading) {
    return <LoadingIndicator />;
  }

  if (tokenExpired) {
    return <TokenExpirationPage />;
  }
  // If there's no weath data history available, render the NoHistoryWeatherPresent component
  if (data == false) {
    return (
      <div className="flex items-center justify-center">
        <NoHistoryWeatherPresent />
      </div>
    );
  } else {
    return (
      <div className="grid grid-col  sm:grid-cols-2 md:grid-cols-4 gap-4 p-4 m-7">
        {results.map((item, index) => (
          <div
            key={index}
            className="bg-black/20 text-black backdrop-blur-[80px] py-12 px-4 shadow-sm rounded-lg overflow-hidden"
          >
            <DisplayWeatherData apiResponse={item} />
          </div>
        ))}
      </div>
    );
  }
};

export default HistoryWeatherData;
