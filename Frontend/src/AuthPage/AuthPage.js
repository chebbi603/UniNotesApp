import React, { useContext, useEffect, useRef, useState } from "react";
import "./authpage.css";
import "../fontstyle.css";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import PropTypes from "prop-types";
import Login from "./Login";
import Register from "./Register";
import gsap from "gsap";
import { useLayoutEffect } from "react";
function AuthPage({ authType }) {
  useLayoutEffect(() => {
    gsap.fromTo(
      ".auth-container",
      {
        y: -10,
        opacity: 0,
      },
      {
        y: 0,
        opacity: 1,
        delay: 0.3,
        duration: 0.8,
        ease: "power3.out",
      }
    );
  }, []);

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
