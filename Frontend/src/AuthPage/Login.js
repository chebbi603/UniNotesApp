import React, { useContext, useState } from "react";
import "./authpage.css";
import "../fontstyle.css";
import img from "../assets/image3.png";
import { Navigate, NavLink } from "react-router-dom";
import AuthContext from "./AuthProvider";
import axios from "../api/axios";
import { IconAlertCircle } from "@tabler/icons-react";
const LOGIN_URL = "/api/users/login";

function Login() {
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const { setAuth } = useContext(AuthContext);
  const [success, setSuccess] = useState(false);
  const [prob, setProb] = useState(false);
  const [message, setMessage] = useState("");
  const [accessToken, setAccessToken] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
    axios
      .post(
        LOGIN_URL,
        {},
        {
          params: { email: email, password: pwd },
          headers: { "Content-Type": "application/json" },
        }
      )
      .then((r) => {
        setAuth({ email, pwd, accessToken });
        localStorage.setItem("userEmail", email);
        localStorage.setItem("accessToken", r.data);
        setEmail("");
        setPwd("");
        setSuccess(true);
      })
      .catch((error) => {
        setProb(true);
        setMessage(error.response.data);
      });
  };

  return success ? (
    <Navigate to="/dashboard-welcome" />
  ) : (
    <div className="auth-container">
      <div className="auth-content">
        <div className="auth-title">
          <p className="h2-bold">Sign in</p>
          <p className="b1-reg">
            Please use your university’s email to access our website
          </p>
        </div>

        <form className="auth-form" onSubmit={handleLogin}>
          <div className="auth-field">
            <p className="b1-bold">Email Address</p>
            <input
              type="email"
              autoComplete="off"
              onChange={(e) => {
                setEmail(e.target.value);
                setProb(false);
              }}
              value={email}
              required
              placeholder="Enter your email address"
              className="auth-input b1-reg"
            ></input>
          </div>
          <div className="auth-field">
            <p className="b1-bold">Password</p>
            <input
              type="password"
              placeholder="Enter your password"
              className="auth-input b1-reg"
              autoComplete="off"
              onChange={(e) => {
                setPwd(e.target.value);
                setProb(false);
              }}
              value={pwd}
              required
            ></input>
          </div>
          {prob === true ? (
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                gap: "8px",
                alignItems: "center",
                height: "20px",
              }}
            >
              <IconAlertCircle color="red"></IconAlertCircle>
              <p style={{ color: "red" }} className="b1-bold error-message">
                {message}
              </p>
            </div>
          ) : (
            <div style={{ height: "20px" }}></div>
          )}
        </form>
        <NavLink to="/dashboard-welcome">
          <button onClick={handleLogin} className="auth-cta h6-bold">
            Log in
          </button>
        </NavLink>
        <NavLink to="/register">
          <a href="#" className="auth-subbutton b1-bold">
            Create an account instead
          </a>
        </NavLink>
      </div>
      <img className="auth-img" src={img} />
    </div>
  );
}

export default Login;
