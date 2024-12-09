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
import notfound from "../assets/notfound.json";
import Lottie from "lottie-react";

const GET_USER_NOTES = "/api/notes/";

function Library() {
  const accessToken = localStorage.getItem("accessToken");
  const [notes, setNotes] = useState([]);
  const [subject, setSubject] = useState([]);

  useEffect(() => {
    window.scrollTo(0, 0);
    const getNotes = () => {
      const mail = localStorage.getItem("userEmail");
      const userId = localStorage.getItem("userId");
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

    getNotes();
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
  }, []);

  return (
    <>
      <Navbar isLoggedIn={1} navCount={0} />
      <div className="main-container">
        <p className="h4-bold black">{subject.name} My Notes</p>
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
          <div className="image-text-container">
            <Lottie
              animationData={notfound}
              loop={true}
              style={{ width: "500px" }}
            ></Lottie>
            <p className="h5-reg black" style={{ textAlign: "center" }}>
              It looks like that you haven't created any note yet
            </p>
          </div>
        )}
      </div>
      <Footer />
    </>
  );
}

export default Library;
