import React from "react";
import PropTypes from "prop-types";
import "./notecard.css";
import { NavLink } from "react-router-dom";

function CourseCard({ id, name, color }) {
  return (
    <NavLink to={"/subject/" + id} style={{ textDecoration: "none" }}>
      <div className="course-card" style={{ backgroundColor: `${color}` }}>
        <p className="h5-bold black course-name">{name}</p>
      </div>
    </NavLink>
  );
}

CourseCard.propTypes = {
  name: PropTypes.string,
  id: PropTypes.number,
  color: PropTypes.string,
};
CourseCard.defaultProps = {
  id: 0,
  name: "Course Name",
  color: "#0000ff",
};

export default CourseCard;
