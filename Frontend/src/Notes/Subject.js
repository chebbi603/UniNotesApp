import React, {
  useCallback,
  useEffect,
  useLayoutEffect,
  useState,
} from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import "./notes.css";
import axios from "../api/axios";
import { useParams } from "react-router-dom";
import NoteCard from "../Dashboard/NoteCard/NoteCard";
import gsap from "gsap";
const GET_NOTES_SUBJECT_URL = "/api/notes/subject/";
const GET_SUBJECT = "/api/subjects/";

function Subject() {
  const accessToken = localStorage.getItem("accessToken");
  const [notes, setNotes] = useState([]);

  const [subject, setSubject] = useState([]);

  const { subjectId } = useParams();

  useEffect(() => {
    window.scrollTo(0, 0);
    const getNotes = () => {
      axios
        .get(GET_NOTES_SUBJECT_URL + subjectId, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          setNotes(response.data);
          console.log(response.data);
        })
        .catch((error) => console.log(error));
    };
    const getSubjectDetails = () => {
      axios
        .get(GET_SUBJECT + subjectId, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          setSubject(response.data);
        })
        .catch((error) => console.log(error));
    };
    getNotes();
    getSubjectDetails();
  }, []);

  useLayoutEffect(() => {
    gsap.fromTo(
      ".main-container",
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
    gsap.fromTo(
      ".pdf-container",
      {
        y: -10,
        opacity: 0,
      },
      {
        y: 0,
        opacity: 1,
        delay: 0.8,
        duration: 0.8,
        ease: "power3.out",
      }
    );
  }, []);

  return (
    <>
      <Navbar isLoggedIn={1} navCount={0} />
      <div className="main-container">
        <p className="h4-bold black">{subject.name} Notes</p>
        {notes.length > 0 ? (
          <div className="notes-container">
            {notes.map((note) => (
              <NoteCard
                name={note.title}
                author={note.message}
                subject={note.subject.name}
                id={note.id}
              />
            ))}
          </div>
        ) : (
          <p>No notes found</p>
        )}
      </div>
      <Footer />
    </>
  );
}

export default Subject;
