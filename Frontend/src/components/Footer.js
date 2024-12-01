import React from "react";
import whitelogo from "../assets/logo-white.svg";
import "./styles.css";
function Footer() {
  return (
    <div className="footer">
      <div className="footer-container">
        <img className="footer-logo" src={whitelogo} />
        <div className="footer-info">
          <p className="footer-desc b1-reg">
            Software Development for Engineers
            <br />
            Fall 2024
          </p>
          <a
            href="https://github.com/chebbi603/UniNotesApp"
            className="footer-link b1-bold"
            target="_blank"
          >
            Github
          </a>
          <a
            href="https://trello.com/invite/b/67082b76a0212ba2098df598/ATTI6b3df658e15c94f629809405c1ffc7541E941C75/unideb-notes-app-softeng-project"
            className="footer-link b1-bold"
            target="_blank"
          >
            Trello
          </a>
        </div>
      </div>
    </div>
  );
}
export default Footer;
