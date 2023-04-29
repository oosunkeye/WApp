import axios from "axios";

import { API_BASE_URL } from "../common/constants";

const frameToken = (token) => `Bearer ${token}`;

const frameResponse = (
  reqStatus = 0,
  reqPayLoad = "Invalid request. Please try again later"
) => {
  return {
    status: reqStatus,
    payLoad: reqPayLoad,
  };
};

export const signUpApi = async (
  username,
  password,
  emailId,
  firstName,
  lastName,
  phone
) => {
  let response = frameResponse();
  try {
    const url = `${API_BASE_URL}/user/signup`;
    const apiResponse = await axios.post(url, {
      firstName,
      lastName,
      username,
      phone,
      emailId,
      password,
    });
    if (apiResponse.status === 200) {
      response = frameResponse(1);
    }
  } catch (err) {
    if (err.response) {
      response = frameResponse(0, err.response.data.message);
    }
    console.log(err);
  } finally {
    return response;
  }
};

export const verifyEmailApi = async (token) => {
  let response = frameResponse();

  try {
    // Verify the email API from the backend
    const url = `${API_BASE_URL}/user/verify/email`;
    const apiResponse = await axios.get(url, {
      headers: { Authorization: frameToken(token) },
    });

    if (apiResponse.status === 200) {
      response = frameResponse(1, apiResponse.data);
    }
  } catch (err) {
    if (err.response) {
      response = frameResponse(0, err.response.data.message);
    } else {
      console.log(err);
    }
  } finally {
    return response;
  }
};

export const loginApi = async (username, password) => {
  let response = frameResponse();
  try {
    const url = `${API_BASE_URL}/user/login`;
    const apiResponse = await axios.post(url, { username, password });
    if (apiResponse.status === 200) {
      const payLoad = {
        userData: apiResponse.data,
        token: apiResponse.headers.authorization,
      };
      response = frameResponse(1, payLoad);
    }
  } catch (err) {
    if (err.response) {
      response = frameResponse(0, err.response.data.message);
    }
    console.log(err);
  } finally {
    return response;
  }
};

export const resetEmailLinkApi = async (emailId) => {
  let response = frameResponse();

  try {
    const url = `${API_BASE_URL}/user/reset/${emailId}`;
    const apiResponse = await axios.get(url);
    if (apiResponse.status === 200) {
      response = frameResponse(1);
    }
  } catch (err) {
    if (err.response) {
      response = frameResponse(0, err.response.data.message);
    }
    console.log(err);
  } finally {
    return response;
  }
};

export const resetPasswordApi = async (token, password) => {
  let response = frameResponse();

  try {
    const url = `${API_BASE_URL}/user/reset?password=${password}`;
    const apiResponse = await axios.post(url, null, {
      headers: { Authorization: frameToken(token) },
    });
    if (apiResponse.status === 200) {
      response = frameResponse(1);
    }
  } catch (err) {
    if (err.response) {
      response = frameResponse(0, err.response.data.message);
    }
    console.log(err);
  } finally {
    return response;
  }
};
//gets weather data from an api and returns response
export const getWeatherDataApi = async (token, location, save) => {

  const url = `${API_BASE_URL}/weathers/${location}/${save}`;

//sets headers for http request
  const headers = {
    headers: { Authorization: frameToken(token)},
  };
  //make get request using axios
  const apiResponse = await axios.get(url,headers);
//return response
  return apiResponse;
};

// API to fetch all weather data searched by the user
export const getHistoryWeatherDataApi = async (token) => {
  let response = undefined; // Initialize response variable to undefined
console.log(token);
  try {
    const url = `${API_BASE_URL}/weathers`; // API endpoint to fetch weather data
    const headers = { headers: { Authorization: frameToken(token) } }; // Pass the token in the request headers
    const apiResponse = await axios.get(url, headers); // Make API call to fetch weather data
    if (apiResponse.status === 200) { // Check if response status is successful
      response = apiResponse.data; // If successful, set the response variable to the data received
    }
  } catch (err) { // Catch any errors that may occur during the API call
    if (err.response) {
      response = err; // If there is a response, set the response variable to the error object
    } console.log(err); // Log the error in the console
  } finally { // Regardless of success or failure, return the response variable
    return response;
  }
};
