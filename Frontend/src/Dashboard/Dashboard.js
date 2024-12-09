import react from "react";
import "./dashboard.css";
import Navbar from "../components/Navbar";
import NoteCard from "./NoteCard/NoteCard";
import CourseCard from "./NoteCard/CourseCard";
import Footer from "../components/Footer";
import { useState, useEffect } from "react";
import gsap from "gsap";
import { useLayoutEffect } from "react";
import PropTypes from "prop-types";
import axios from "../api/axios";

const GET_USER_URL = "/api/users/user";
const GET_MAJOR_URL = "/api/subjects/major";
const GET_USER_NOTES = "/api/notes/";
const GET_NOTES = "/api/notes/getAll/";

function Dashboard({ logged }) {
  const accessToken = localStorage.getItem("accessToken");
  const [data, setData] = useState([]);
  const [name, setName] = useState("");
  const [major, setMajor] = useState("");
  const [userId, setUserId] = useState(0);
  const [subjects, setSubjects] = useState([]);
  const [notes, setNotes] = useState([]);
  const [allNotes, setAllNotes] = useState([]);
  const colors = [
    "#F4CC0B",
    "#FB369F",
    "#0BA2F4",
    "#0BF445",
    "#3DA885",
    "#fdf2af",
  ];
  useEffect(() => {
    window.scrollTo(0, 0);
    const getData = () => {
      const mail = localStorage.getItem("userEmail");
      axios
        .get(GET_USER_URL, {
          params: { email: mail },
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          setData(response.data);
          setName(response.data.name);
          setUserId(response.data.id);
          localStorage.setItem("userId", response.data.id);
          setMajor(response.data.major);
        })
        .catch((error) => console.log(error));
    };

    const getNotes = () => {
      const mail = localStorage.getItem("userEmail");
      axios
        .get(GET_USER_NOTES + userId + "/notes", {
          params: { email: mail },
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          setNotes(response.data);
        })
        .catch((error) => console.log(error));
    };

    const getAllNotes = () => {
      const mail = localStorage.getItem("userEmail");
      axios
        .get(GET_NOTES, {
          params: { email: mail },
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          setAllNotes(response.data);
          console.log(response.data);
        })
        .catch((error) => console.log(error));
    };

    const getSubjects = () =>
      axios
        .get(GET_MAJOR_URL, {
          params: {
            major: major,
          },
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setSubjects(res.data);
        })
        .catch((error) => {
          console.log(error);
        });
    getData();
    getSubjects();
    getAllNotes();
    getNotes();
  }, [name, major]);

  useLayoutEffect(() => {
    if (logged === 0) {
      gsap.fromTo(
        ".navbar",
        {
          y: -30,
          opacity: 0,
        },
        {
          y: 0,
          opacity: 1,
          delay: 1,
          duration: 0.5,
          ease: "power3.out",
        }
      );

      gsap.fromTo(
        ".main-container",
        {
          x: -40,
          opacity: 0,
        },
        {
          x: 0,
          opacity: 1,
          duration: 0.8,
          ease: "power3.out",
        }
      );

      gsap.fromTo(
        ".dashboard-recent-container",
        {
          opacity: 0,
          y: 30,
        },
        {
          y: 0,
          opacity: 1,
          stagger: 0.5,
          delay: 1,
          duration: 1,
          ease: "elastic.out(1,0.75)",
        }
      );
    } else {
      gsap.fromTo(
        ".main-container",
        {
          x: -40,
          opacity: 0,
        },
        {
          x: 0,
          opacity: 1,
          duration: 0.8,
          ease: "power3.out",
        }
      );

      gsap.fromTo(
        ".dashboard-recent-container",
        {
          opacity: 0,
          y: 30,
        },
        {
          y: 0,
          opacity: 1,
          stagger: 0.5,
          delay: 0.5,
          duration: 1,
          ease: "elastic.out(1,0.75)",
        }
      );
    }
  }, []);

  return (
    <div className="dashboard">
      <Navbar isLoggedIn={1} navCount={1} />
      <div className="main-container">
        <div className="dashboard-welcome">
          <img
            className="dashboard-welcome-avatar"
            src="https://cdn3.emoji.gg/emojis/8015-book-hat.png"
          ></img>
          <div className="dashboard-welcome-message">
            <p className="h5-bold black">Welcome back</p>
            {name ? <p className="h5-bold secondary">{name} 👋</p> : null}
          </div>
        </div>
        <div className="dashboard-recent-container">
          <p className="h5-bold black">Popular Notes</p>
          <div className="dashboard-cardlist">
            {allNotes.map((note) => (
              <NoteCard
                name={note.title}
                author={note.message}
                subject={note.subject.name}
                id={note.id}
              />
            ))}
          </div>
        </div>
        <div className="dashboard-recent-container">
          <div className="dashboard-section-header">
            <p className="h5-bold black">
              <span className="primary">{major} </span>Courses
            </p>
            <a className="h6-bold secondary">View All</a>
          </div>
          <div className="dashboard-cardlist">
            {subjects.map((subject, i) =>
              i < 6 ? (
                <CourseCard
                  id={subject.id}
                  name={subject.name}
                  color={colors[i % 6]}
                />
              ) : (
                <></>
              )
            )}
          </div>
        </div>
        {notes.length > 0 ? (
          <div className="dashboard-recent-container">
            <p className="h5-bold black">My Notes</p>

            <div className="dashboard-cardlist">
              {notes.map((note) => (
                <NoteCard
                  name={note.title}
                  author={note.message}
                  subject={note.subject.name}
                  id={note.id}
                />
              ))}
            </div>
          </div>
        ) : (
          <></>
        )}
      </div>
      <Footer></Footer>
    </div>
  );
}

Dashboard.propTypes = {
  logged: PropTypes.number,
};

Dashboard.defaultProps = {
  logged: 1,
};

export default Dashboard;
