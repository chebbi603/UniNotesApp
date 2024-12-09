import React, { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";
import "./styles.css";
import "../fontstyle.css";
import navbarlogo from "../assets/notes-logo-nav.svg";
import { useNavigate, NavLink } from "react-router-dom"; // Import useNavigate hook
import {
  IconBell,
  IconMessage,
  IconNotification,
  IconSearch,
} from "@tabler/icons-react";

const Navbar = ({ navCount, navButton, isLoggedIn }) => {
  const [keyword, setKeyword] = useState("");
  const myElementRef = useRef(null);
  const navigate = useNavigate(); // Initialize the navigate function

  useEffect(() => {
    const element = myElementRef.current;

    if (element) {
      const handleKeyDown = (e) => {
        if (e.code === "Enter") {
          // If "Enter" is pressed, navigate to the search page with the keyword
          navigate(`/search/${keyword}`);
        }
      };
      const handleClick = (e) => {
        // If "Enter" is pressed, navigate to the search page with the keyword
        navigate(`/search/`);
      };

      // Add event listener for the "Enter" keydown event
      element.addEventListener("keydown", handleKeyDown);
      element.addEventListener("click", handleClick);

      // Cleanup the event listener on component unmount
      return () => {
        element.removeEventListener("keydown", handleKeyDown);
        element.addEventListener("click", handleClick);
      };
    }
  }, [keyword, navigate]);

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
                className={`navbar__link ${
                  navCount === 1 ? "b1-bold active" : "b1-reg"
                }`}
              >
                Home
              </NavLink>
            </li>
            <li>
              <a
                href=""
                className={`navbar__link ${
                  navCount === 3 ? "b1-bold active" : "b1-reg"
                }`}
              >
                Explore
              </a>
            </li>
            <li>
              <a
                href=""
                className={`navbar__link ${
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
                className={`navbar__link ${
                  navCount === 1 ? "b1-bold active" : "b1-reg"
                }`}
              >
                Dashboard
              </NavLink>
            </li>

            <li>
              <NavLink
                to="/library"
                className={`navbar__link ${
                  navCount === 4 ? "b1-bold active" : "b1-reg"
                }`}
              >
                My Library
              </NavLink>
            </li>
          </ul>
          <div className="input-container">
            <IconSearch />
            <input
              placeholder="Search for anything"
              className="search-input b1-reg"
              ref={myElementRef}
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)} // Update keyword as user types
            />
          </div>
          <div className="nav-buttons">
            <div className="navbar__cta">
              <NavLink to="/addnote" className="navbar__controller">
                <button className="navbar__button b1-bold">Create note</button>
              </NavLink>
            </div>
            <IconMessage className="nav-button-icon" size={32} />
            <IconBell className="nav-button-icon" size={32} />
            <NavLink to="/profile">
              {navCount === 5 ? (
                <img
                  className="nav-avatar-selected"
                  src="https://cdn3.emoji.gg/emojis/8015-book-hat.png"
                />
              ) : (
                <img
                  className="nav-avatar"
                  src="https://cdn3.emoji.gg/emojis/8015-book-hat.png"
                />
              )}
            </NavLink>
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
