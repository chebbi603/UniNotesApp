import React, { useContext, useEffect, useRef, useState } from "react";
import "./authpage.css";
import "../fontstyle.css";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import PropTypes from "prop-types";
import Login from "./Login";
import Register from "./Register";

function AuthPage({ authType }) {
  return (
    <div className="auth-page">
      <Navbar navCount={0} navButton={0} isLoggedIn={0} />
      {authType === 0 ? <Register /> : <Login />}
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
