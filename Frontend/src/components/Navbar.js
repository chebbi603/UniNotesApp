import React from "react";
import PropTypes from "prop-types";
import "./styles.css";
import "../fontstyle.css";
import navbarlogo from "../assets/notes-logo-nav.svg";
import { NavLink } from "react-router-dom";
import {
  IconBell,
  IconMessage,
  IconNotification,
  IconSearch,
} from "@tabler/icons-react";

const Navbar = ({ navCount, navButton, isLoggedIn }) => {
  return (
    <nav className="navbar">
      {isLoggedIn === 0 ? (
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
              className={`navbar__controller ${
                navButton === 0 ? "hidden" : ""
              }`}
            >
              <button className="navbar__button b1-bold">Join now</button>
            </NavLink>
          </div>
        </div>
      ) : (
        <div className="nav-container-max">
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
                to="/dashboard"
                className={` navbar__link ${
                  navCount === 1 ? "b1-bold active" : "b1-reg"
                }`}
              >
                Dashboard
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
                My Library
              </a>
            </li>
          </ul>
          <div className="input-container">
            <IconSearch></IconSearch>
            <input
              placeholder="Search for anything"
              className="search-input b1-reg"
            ></input>
          </div>
          <div className="nav-buttons">
            <div className="navbar__cta">
              <NavLink to="/addnote" className="navbar__controller">
                <button className="navbar__button b1-bold">Create note</button>
              </NavLink>
            </div>
            <IconMessage className="nav-button-icon" size={32}></IconMessage>
            <IconBell className="nav-button-icon" size={32}></IconBell>
            <img
              className="nav-avatar"
              src="https://scontent-vie1-1.xx.fbcdn.net/v/t39.30808-6/439438068_2589492631231246_1843397142712707744_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=vwUTJuB-MRwQ7kNvgEdxVlz&_nc_zt=23&_nc_ht=scontent-vie1-1.xx&_nc_gid=ADcZWIeZXapIfmcB8ZBsLpc&oh=00_AYAecbVyerzdjkXBUA-Pk-ewL38yL8eAFjbd0tsRjSBwjg&oe=67533A4D"
            />
          </div>
        </div>
      )}
    </nav>
  );
};

Navbar.propTypes = {
  navCount: PropTypes.number,
  navButton: PropTypes.number,
  isLoggedIn: PropTypes.number,
};

Navbar.defaultProps = {
  navCount: 1,
  navButton: 1,
  isLoggedIn: 0,
};

export default Navbar;
