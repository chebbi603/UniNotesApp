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
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/addnote" element={<AddNote />} />
        </Routes>
      </div>
    </ReactLenis>
  );
}

export default App;
