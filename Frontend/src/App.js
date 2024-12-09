import logo from "./logo.svg";
import "./App.css";
import gsap from "gsap";
import HomePage from "./HomePage/Homepage";
import AuthPage from "./AuthPage/AuthPage";
import { Routes, Route } from "react-router-dom";
import { useRef } from "react";
import ReactLenis from "lenis/react";
import Dashboard from "./Dashboard/Dashboard";
import AddNote from "./Notes/AddNote";
import ProfilePage from "./Dashboard/ProfilePage/ProfilePage";
import ViewNote from "./Notes/ViewNote";
import Subject from "./Notes/Subject";
import Search from "./Notes/Search";
import Library from "./Notes/Library";

function App() {
  const zoomLevel = Math.round(window.devicePixelRatio * 100);
  console.log(zoomLevel);
  document.documentElement.style.zoom = "0.9";
  const el = useRef();
  return (
    <ReactLenis root ref={el}>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<AuthPage authType={1} />} />
          <Route path="/register" element={<AuthPage authType={0} />} />
          <Route path="/verify" element={<AuthPage authType={2} />} />
          <Route
            path="/verification-success"
            element={<AuthPage authType={3} />}
          />
          <Route path="/dashboard" element={<Dashboard logged={1} />} />
          <Route path="/dashboard-welcome" element={<Dashboard logged={0} />} />
          <Route path="/addnote" element={<AddNote />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/note/:noteId" element={<ViewNote />} />
          <Route path="/subject/:subjectId" element={<Subject />} />
          <Route path="/search/:keyword" element={<Search />} />
          <Route path="/search" element={<Search />} />
          <Route path="/library" element={<Library />} />
        </Routes>
      </div>
    </ReactLenis>
  );
}

export default App;
