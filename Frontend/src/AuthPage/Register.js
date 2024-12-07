import React, { useState } from "react";
import "./authpage.css";
import "../fontstyle.css";
import Navbar from "../components/Navbar";
import img from "../assets/image3.png";
import { NavLink } from "react-router-dom";
import axios from "../api/axios";
import { HttpStatusCode } from "axios";

const REGISTER_URL = "/api/users/register";
const VERIFY_URL = "/api/users/verify";

function Register() {
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [name, setName] = useState("");
  const [major, setMajor] = useState("");
  const [code, setCode] = useState("");
  const [registerMode, setRegisterMode] = useState(1);

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        REGISTER_URL,
        {
          email: email,
          password: pwd,
          name: name,
          major: major,
        },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      if (response?.status == HttpStatusCode.Ok) setRegisterMode(2);
    } catch (error) {
      console.log("error");
    }
  };

  const handleVerify = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        VERIFY_URL,
        {},
        {
          params: { email, code },
          headers: { "Content-Type": "application/json" },
        }
      );
      if (response?.status === HttpStatusCode.Ok) setRegisterMode(3);
    } catch (error) {
      console.log("error");
    }
  };

  return registerMode === 1 ? (
    <div className="auth-container">
      <div className="auth-content">
        <div className="auth-title">
          <p className="h2-bold">Create an account</p>
          <p className="b1-reg">
            Please use your university’s email to access our website
          </p>
        </div>
        <div className="auth-form">
          <div className="auth-field">
            <p className="b1-bold">Full name</p>
            <input
              placeholder="Enter your full name"
              onChange={(e) => setName(e.target.value)}
              className="auth-input b1-reg"
            ></input>
          </div>
          <div className="auth-field">
            <p className="b1-bold">Email Address</p>
            <input
              type="email"
              placeholder="Enter your email address"
              onChange={(e) => setEmail(e.target.value)}
              className="auth-input b1-reg"
            ></input>
          </div>
          <div className="auth-field">
            <p className="b1-bold">Password</p>
            <input
              type="password"
              onChange={(e) => setPwd(e.target.value)}
              placeholder="Enter your password"
              className="auth-input b1-reg"
            ></input>
          </div>
          <div className="auth-field">
            <p className="b1-bold">Current Major</p>
            <div className="major-container">
              <select
                onChange={(e) => setMajor(e.target.value)}
                className="auth-input b1-reg"
              >
                <option value="">Select major</option>
                <option value="CSE">Computer Science Engineering</option>
                <option value="BI">Business Informatics</option>
                <option value="CS">Computer Science</option>
              </select>
            </div>
          </div>
        </div>
        <NavLink className="auth-link" to="/verify">
          <button onClick={handleRegister} className="auth-cta h6-bold">
            Sign up
          </button>
        </NavLink>
        <NavLink to="/login">
          <a href="#" className="auth-subbutton b1-bold">
            Already have an account? Log in instead
          </a>
        </NavLink>
      </div>
      <img img className="auth-img" src={img} />
    </div>
  ) : registerMode === 2 ? (
    <>
      <div className="auth-container">
        <div className="auth-content">
          <div className="auth-title">
            <p className="h2-bold">Verify your Email</p>
            <p className="b1-reg">
              Please check your inbox to find your verification code
            </p>
          </div>
          <div className="auth-form">
            <div className="auth-field">
              <p className="b1-bold">Verification Code</p>
              <input
                onChange={(e) => setCode(e.target.value)}
                placeholder="Enter your verification code"
                className="auth-input b1-reg"
              ></input>
            </div>
          </div>
          <NavLink onClick={handleVerify} to="/dashboard">
            <button className="auth-cta h6-bold">Verify</button>
          </NavLink>
        </div>
        <img className="auth-img" src={img} />
      </div>
    </>
  ) : (
    <>
      <div className="auth-page">
        <Navbar navCount={0} navButton={0} isLoggedIn={0} />
        <div className="auth-container">
          <div className="auth-content">
            <div className="auth-title">
              <p className="h2-bold">Your account has been verified</p>
              <p className="b1-reg">Please log in to our website to proceed</p>
            </div>
            <NavLink to="/login">
              <button className="auth-cta h6-bold">Log in</button>
            </NavLink>
          </div>
        </div>
      </div>
    </>
  );
}

export default Register;
