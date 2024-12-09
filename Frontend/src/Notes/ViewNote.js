import React, { useCallback, useEffect } from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import { IconDownload, IconFilePlus } from "@tabler/icons-react";
import { useDropzone } from "react-dropzone";
import "./notes.css";
import PropTypes, { node } from "prop-types";
import { useParams } from "react-router-dom";
import PDFViewer from "./PdfViewer";
import { GlobalWorkerOptions } from "pdfjs-dist";
import { useState, useLayoutEffect } from "react";
import axios from "../api/axios";
import gsap from "gsap";
import ScrollTrigger from "gsap/ScrollTrigger";
const GET_NOTE_URL = "/api/notes/";

function ViewNote() {
  const accessToken = localStorage.getItem("accessToken");
  const { noteId } = useParams();
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [subject, setSubject] = useState("");
  const [description, setDescription] = useState("");
  const [date, setDate] = useState("");

  useEffect(() => {
    window.scrollTo(0, 0);
    const getNote = () =>
      axios
        .get(GET_NOTE_URL + noteId, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          console.log(res.data);
          setAuthor(res.data.author.name);
          setTitle(res.data.title);
          setDescription(res.data.message);
          setSubject(res.data.subject.name);
        })
        .catch((error) => {
          console.log(error);
        });
    getNote();
  }, [title, description, author, subject, date]);

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
        <div className="note-view-content">
          <div className="note-header">
            <p className="h4-bold black">{title}</p>
            <p className="h6-reg black">{description}</p>
          </div>
          <div className="note-header">
            <p className="b1-reg black">Published by: {author}</p>
            <p className="b1-bold primary">Subject: {subject}</p>
          </div>
          <PDFViewer fileId={noteId} />
        </div>
      </div>

      <Footer />
    </>
  );
}

export default ViewNote;
