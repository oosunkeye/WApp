import React, { useState, useContext, useRef } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
// import BPTN from "../images/BPTN-logo.png";
import BPTN from "../images/linkedIn.png";
import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";
import { AiFillEyeInvisible, AiFillEye } from "react-icons/ai";
import { loginApi } from "../util/ApiUtil";
import { AppContext } from "../Context/applicationContext";
import LoadingIcon from "../components/LoadingTemp";
import Lottie from "lottie-react";

import weather from "../user/weather.json";

import {
  USERNAME_MIN_LENGTH,
  USERNAME_MAX_LENGTH,
  PASSWORD_MIN_LENGTH,
  PASSWORD_MAX_LENGTH,
} from "../common/constants";

const Login = () => {
  const [open, setOpen] = useState(false);
  let navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const formikRef = useRef();
  let appContext = useContext(AppContext);

  const onFormSubmit = async (values, actions) => {
    setLoading(true);
    const apiResponse = await loginApi(values.username, values.password);
    const payLoad = apiResponse.payLoad;

    if (apiResponse.status === 1) {
      setLoading(false);
      appContext.setSession(payLoad);
      toast("Login succesful.");
    } else {
      actions.resetForm();
      toast(apiResponse.payLoad);
    }
  };

  const toggle = () => {
    setOpen(!open);
  };

  const LoginSchema = Yup.object().shape({
    username: Yup.string()
      .min(USERNAME_MIN_LENGTH, "Too short")
      .max(USERNAME_MAX_LENGTH, "Too long")
      .required("User Name cannot be empty"),
    password: Yup.string()
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/,
        "Must Contain 8 Characters, One Uppercase, One Lowercase, One Number and One Special Case Character"
      )
      .min(PASSWORD_MIN_LENGTH, "Too short")
      .max(PASSWORD_MAX_LENGTH, "Too long")
      .required("Password cannot be blank"),
  });

  return (
    <Formik
      innerRef={formikRef}
      initialValues={{
        username: "",
        password: "",
      }}
      validationSchema={LoginSchema}
      onSubmit={onFormSubmit}
    >
      <div className="flex flex-col items-center  min-h-screen pt-6 sm:justify-center sm:pt-0 b">
        {/* <video
          autoPlay
          loop
          muted
          playsInline
          style={{ position: "absolute", right: 0, bottom: 0, zIndex: -1 }}
        >
          <source src={weathervideo2} type="video/mp4"></source>
        </video> */}
        <div className="w-full px-6 py-4 mt-6 overflow-hidden text-center  static bg-white shadow-2xl sm:max-w-lg sm:rounded-lg">
          <div className="bg-gradient-to-r from-cyan-500 to-blue-500  shadow-2xl p-5 rounded-full absolute -mt-20 mx-40 ">
            <Lottie className="h-20 w-20" loop={true} animationData={weather} />
          </div>
          <h1 className="text-4xl text-cColor-50 font-bold text-center pt-20">
            Welcome to Weather app
          </h1>
          <p className="text-black-600 text-base font-medium px-3">
            Everything you need to know about weather is only one glance away
            now!
          </p>
          <Form>
            <div className="mt-4">
              <label
                class="block text-cColor-50 font-medium text-md mb-2 text-left"
                for="grid-first-name"
              >
                Username
              </label>

              <div className="flex flex-col items-start">
                <Field
                  type="text"
                  name="username"
                  id="username"
                  placeholder="Enter your username"
                  className="block w-full mt-1 p-3 text-lg bg-[#DBE7EF] border-red-300 rounded-md shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
                />

                <div className="text-red-600 text-xs italic">
                  {" "}
                  <ErrorMessage name="username" />
                </div>
              </div>
            </div>

            <div className="mt-4">
              <label
                class="block text-cColor-50 font-medium text-md mb-2 text-left"
                for="grid-first-name"
              >
                Password
              </label>

              <div className="flex flex-row items-start">
                <Field
                  type={open === false ? "password" : "text"}
                  name="password"
                  id="password"
                  placeholder="Enter your password"
                  className="block w-full mt-1 p-3 text-lg border-gray-300 rounded-md shadow-sm bg-[#DBE7EF] focus:border-indigo-300 focus:ring focus:ring-indigo-200"
                />
                <span className="text-2xl -ml-8 mt-4">
                  {open === false ? (
                    <AiFillEye onClick={toggle} />
                  ) : (
                    <AiFillEyeInvisible onClick={toggle} />
                  )}
                </span>
              </div>
              <div className="text-red-600 text-xs italic pr-[20rem]">
                {" "}
                <ErrorMessage name="password" />
              </div>
            </div>
            <div className="pl-[23rem]">
              <Link
                to="/resetEmailLink"
                className="text-xs text-cColor-50 text-right font-medium hover:underline hover:text-cColor-50"
              >
                Forgot Password?
              </Link>
            </div>
            <div className="flex items-center mt-4">
              <button
                className="w-full text-xl font-medium px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-cColor-50 rounded-md hover:bg-cColor-100 focus:outline-none focus:bg-cColor-50"
                type="submit"
              >
                {loading ? <LoadingIcon /> : "Login"}
              </button>
            </div>
          </Form>

          <div className="flex items-center w-full my-4">
            <hr className="w-full" />
            <p className="px-3 ">OR</p>
            <hr className="w-full" />
          </div>

          <div className="mt-4 font-medium">
            Dont have an account?{" "}
            <span>
              <Link to="/signup" className="text-cColor-50 hover:underline">
                Sign up
              </Link>
            </span>
          </div>

          <div className="my-6 space-y-2">
            <div className="flex items-center justify-center w-full p-2 space-x-4 border rounded-md focus:ring-2 focus:ring-offset-1 dark:border-gray-400 focus:ring-violet-400">
              <p>Powered by</p>
              <img className="h-10" src={BPTN} />
            </div>
          </div>
        </div>
      </div>
    </Formik>
  );
};

export default Login;
