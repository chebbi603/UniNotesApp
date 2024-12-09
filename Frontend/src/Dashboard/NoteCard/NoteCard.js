import React, { useEffect, useState } from "react";
import "./notecard.css";
import PropTypes from "prop-types";
import { NavLink } from "react-router-dom";
import axios from "../../api/axios";
import PdfThumbnail from "../../Notes/PdfThumbnail";

const GET_SUBJECT_URL = "/api/subjects/";

function NoteCard({ id, cover, name, author, subject }) {
  return (
    <NavLink to={`/note/${id}`} style={{ textDecoration: "none" }}>
      <div className="notecard-container">
        <div className="notecard-text-container">
          <p className="notecard-author notecard-text b2-bold primary">
            {subject}
          </p>
          <p className="notecard-title notecard-text h5-bold black">{name}</p>
          <p className="notecard-author notecard-text b1-reg black">{author}</p>
        </div>
      </div>
    </NavLink>
  );
}

NoteCard.propTypes = {
  id: PropTypes.number,
  cover: PropTypes.string,
  name: PropTypes.string,
  author: PropTypes.string,
  subject: PropTypes.string,
};

NoteCard.defaultProps = {
  id: 0,
  cover: "",
  name: "Dummy note",
  author: "Flen Fouleni",
  subject: 1,
};

export default NoteCard;
