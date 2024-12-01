import React from "react";
import "./authpage.css";
import "../fontstyle.css";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import img from "../assets/image3.png";
import PropTypes from "prop-types";

function AuthPage({ authType }) {
  return authType == 1 ? (
    <div className="auth-page">
      <Navbar navCount={0} navButton={0} />
      <div className="auth-container">
        <div className="auth-content">
          <div className="auth-title">
            <p className="h2-bold">Sign in</p>
            <p className="b1-reg">
              Please use your university’s email to access our website
            </p>
          </div>
          <div className="auth-form">
            <div className="auth-field">
              <p className="b1-bold">Email Address</p>
              <input
                type="email"
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
              ></input>
            </div>
          </div>
          <button className="auth-cta h6-bold">Log in</button>
          <a href="#" className="auth-subbutton b1-bold">
            Create an account instead
          </a>
        </div>
        <img src={img} />
      </div>
      <Footer />
    </div>
  ) : (
    <div className="auth-page">
      <Navbar navCount={0} navButton={0} />
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
                className="auth-input b1-reg"
              ></input>
            </div>
            <div className="auth-field">
              <p className="b1-bold">Email Address</p>
              <input
                type="email"
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
              ></input>
            </div>
            <div className="auth-field">
              <p className="b1-bold">Current Major</p>
              <div className="major-container">
                <select className="auth-input b1-reg major-select">
                  <option value="Bsc">Bsc</option>
                  <option value="Msc">Msc</option>
                  <option value="PhD">PhD</option>
                </select>
                <select className="auth-input b1-reg">
                  <option value="">Select major</option>
                  <option value="CSE">Computer Science Engineering</option>
                  <option value="BI">Business Informatics</option>
                  <option value="CS">Computer Science</option>
                </select>
              </div>
            </div>
          </div>
          <button className="auth-cta h6-bold">Sign up</button>
          <a href="#" className="auth-subbutton b1-bold">
            Already have an account? Log in instead
          </a>
        </div>
        <img src={img} />
      </div>
      <Footer />
    </div>
  );
}
AuthPage.propTypes = {
  authType: PropTypes.number,
};

Navbar.defaultProps = {
  authType: 1,
};
export default AuthPage;
