import React, {
  useCallback,
  useEffect,
  useState,
  useLayoutEffect,
} from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import { IconDownload, IconFilePlus } from "@tabler/icons-react";
import { useDropzone } from "react-dropzone";
import "./notes.css";
import { Navigate, NavLink } from "react-router-dom";
import axios from "../api/axios";
import gsap from "gsap";

const UPLOAD_URL = "/api/notes/create";
const SUBJECT_URL = "/api/subjects/getAll";
const GET_USER_URL = "/api/users/user";
const GET_MAJOR_URL = "/api/subjects/major";

function AddNote() {
  const accessToken = localStorage.getItem("accessToken");
  const [noteName, setNoteName] = useState("");
  const [noteDescription, setNoteDescription] = useState("");
  const [subject, setSubject] = useState(0);
  const [isPublic, setIsPublic] = useState(true);
  const [file, setFile] = useState(null);
  const [userId, setUserId] = useState();
  const [subjects, setSubjects] = useState([]);
  const [data, setData] = useState([]);
  const [success, setSuccess] = useState(false);
  const [major, setMajor] = useState("");

  const uploadFile = () => {
    try {
      const formData = new FormData();
      formData.append("title", noteName);
      formData.append("message", noteDescription);
      formData.append("subjectId", subject);
      formData.append("isPublic", isPublic);
      formData.append("file", file);
      formData.append("userId", userId);

      axios.post(UPLOAD_URL, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      });

      setSuccess(true);
    } catch (error) {
      console.log(error);
    }
  };

  const onDrop = useCallback((acceptedFiles) => {
    console.log(acceptedFiles[0]);
    setFile(acceptedFiles[0]);
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  useEffect(() => {
    const getData = () => {
      const mail = localStorage.getItem("userEmail");
      axios
        .get(GET_USER_URL, {
          params: { email: mail },
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          setData(response.data);
          setUserId(response.data.id);
          setMajor(response.data.major);
        })
        .catch((error) => {
          console.log("error");
        });
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
  }, [userId, success, major]);

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

  if (!success)
    return (
      <div className="dashboard">
        <Navbar isLoggedIn={1} navCount={0} />
        <div className="main-container">
          <p className="h4-bold black ">Upload a new note</p>
          <div className="upload-container">
            <div className="upload-area" {...getRootProps()}>
              <input {...getInputProps()} />

              {isDragActive ? (
                <>
                  <IconDownload size={64} color="#6256CA" />
                  <p className="h5-bold secondary">Drop here</p>
                </>
              ) : (
                <>
                  {file ? (
                    <>
                      <p className="h5-bold secondary">Added 1 File</p>
                    </>
                  ) : (
                    <>
                      <IconFilePlus size={64} color="#6256CA" />
                      <p className="h5-bold secondary">
                        Click here to upload your PDF File
                      </p>
                    </>
                  )}
                </>
              )}
            </div>
            <div className="upload-form">
              <div className="auth-form">
                <div className="auth-field">
                  <p className="b1-bold">Note Title</p>
                  <input
                    placeholder="Choose a title for your note"
                    onChange={(e) => {
                      setNoteName(e.target.value);
                    }}
                    className="auth-input b1-reg"
                  ></input>
                </div>
                <div className="auth-field">
                  <p className="b1-bold">Subject</p>
                  <div className="major-container">
                    <select
                      className="auth-input b1-reg"
                      onChange={(e) => setSubject(e.target.value)}
                    >
                      <option value="">Select Subject</option>
                      {subjects.map((subject) => (
                        <option key={subject.id} value={subject.id}>
                          {subject.name}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div className="auth-field">
                  <p className="b1-bold">Note Description</p>
                  <input
                    onChange={(e) => {
                      setNoteDescription(e.target.value);
                    }}
                    placeholder="Describe what your note is about"
                    className="auth-input b1-reg"
                  ></input>
                </div>

                <div className="auth-field">
                  <p className="b1-bold">Visibility</p>
                  <div
                    style={{
                      display: "flex",
                      flexDirection: "row",
                      gap: "12px",
                      padding: "8px 0px",
                      justifyContent: "flex-start",
                      alignItems: "center",
                    }}
                  >
                    <input
                      type="checkbox"
                      checked={isPublic}
                      onChange={(e) => {
                        setIsPublic(!isPublic);
                      }}
                      placeholder="Choose a title for your note"
                      className="b1-reg"
                      style={{
                        width: "20px",
                        height: "20px",
                      }}
                    ></input>
                    <label className="b1-reg black">
                      Share as a public note
                    </label>
                  </div>
                </div>

                <NavLink
                  onClick={uploadFile}
                  className="auth-link"
                  to="/dashboard"
                >
                  <button className="auth-cta h6-bold">Upload File</button>
                </NavLink>
              </div>
            </div>
          </div>
        </div>
        <Footer />
      </div>
    );
  else return <Navigate to="/dashboard" />;
}

export default AddNote;
