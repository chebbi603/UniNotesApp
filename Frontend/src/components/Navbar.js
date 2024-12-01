import React from "react";
import PropTypes from "prop-types";
import "./styles.css";
import "../fontstyle.css";
import navbarlogo from "../assets/notes-logo-nav.svg";
import { NavLink } from "react-router-dom";

const Navbar = ({ navCount, navButton }) => {
  return (
    <nav className="navbar">
      <div className="nav-container">
        <div className="navbar__logo">
          <img
            src={navbarlogo}
            alt="Unideb Notes Logo"
            className="navbar__logo-image"
          />
        </div>

        <ul className="navbar__links">
          <li>
            <NavLink
              to="/"
              className={` navbar__link ${
                navCount === 1 ? "b1-bold active" : "b1-reg"
              }`}
            >
              Home
            </NavLink>
          </li>
          <li>
            <a
              href=""
              className={` navbar__link ${
                navCount === 3 ? "b1-bold active" : "b1-reg"
              }`}
            >
              Explore
            </a>
          </li>
          <li>
            <a
              href=""
              className={` navbar__link ${
                navCount === 4 ? "b1-bold active" : "b1-reg"
              }`}
            >
              About
            </a>
          </li>
        </ul>

        <div className="navbar__cta">
          <NavLink
            to="/login"
            className={`navbar__controller ${navButton === 0 ? "hidden" : ""}`}
          >
            <button className="navbar__button b1-bold">Join now</button>
          </NavLink>
        </div>
      </div>
    </nav>
  );
};

Navbar.propTypes = {
  navCount: PropTypes.number,
  navButton: PropTypes.number,
};

Navbar.defaultProps = {
  navCount: 1,
  navButton: 1,
};

export default Navbar;
