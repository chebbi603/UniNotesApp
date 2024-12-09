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
import Lottie from "lottie-react";
import searchAnim from "../assets/lottie-search.json";
import notfound from "../assets/notfound.json";
import "../App.css";

const SEARCH_NOTES_URL = "/api/notes/search/";
const GET_USER_NOTES = "/api/notes/getAll/";

function Search() {
  const accessToken = localStorage.getItem("accessToken");
  const [notes, setNotes] = useState([]);
  const [userId, setUserId] = useState(localStorage.getItem("userId"));
  const { keyword } = useParams();

  useEffect(() => {
    window.scrollTo(0, 0);
    const getNotes = () => {
      if (keyword)
        axios
          .get(SEARCH_NOTES_URL + keyword, {
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
      else {
        const mail = localStorage.getItem("userEmail");
        axios
          .get(GET_USER_NOTES, {
            params: { email: mail },
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
      }
    };
    getNotes();
  }, [keyword]);

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
        {keyword ? (
          <p className="h4-bold black">Search Results</p>
        ) : (
          <p className="h4-bold black">Search For Notes</p>
        )}
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
              style={{ width: "400px", height: "400px", scale: "1.6" }}
            ></Lottie>
            <p className="h5-reg black" style={{ textAlign: "center" }}>
              Oops... We can't find any file with this name
              <br />
              Please try changing your search query
            </p>
          </div>
        )}
      </div>
      <Footer />
    </>
  );
}

export default Search;
