import React from "react";
import PropTypes from "prop-types";
import "./NoteCard/notecard.css";

function CourseCard({ name, color }) {
  return (
    <div className="course-card" style={{ backgroundColor: `${color}` }}>
      <p className="h5-bold black course-name">{name}</p>
    </div>
  );
}

CourseCard.propTypes = {
  name: PropTypes.string,
  color: PropTypes.string,
};
CourseCard.defaultProps = {
  name: "Course Name",
  color: "#0000ff",
};

export default CourseCard;
