import React from "react";
import "./notecard.css";
import PropTypes from "prop-types";

function NoteCard({ cover, name, author }) {
  return (
    <div className="notecard-container">
      <img className="notecard-cover" src={cover} />
      <div className="notecard-text-container">
        <p className="notecard-title notecard-text h5-bold black">{name}</p>
        <p className="notecard-author notecard-text b1-reg black">{author}</p>
      </div>
      <button className="notecard-cta h6-bold">Open Note</button>
    </div>
  );
}

NoteCard.propTypes = {
  cover: PropTypes.string,
  name: PropTypes.string,
  author: PropTypes.string,
};

NoteCard.defaultProps = {
  cover: "",
  name: "Dummy note",
  author: "Flen Fouleni",
};

export default NoteCard;
