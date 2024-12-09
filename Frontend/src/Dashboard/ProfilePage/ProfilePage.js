import React, { useEffect, useLayoutEffect } from "react";
import Navbar from "../../components/Navbar";
import Footer from "../../components/Footer";
import "./profilepage.css";
import NoteCard from "../NoteCard/NoteCard";
import axios from "../../api/axios";
import { useState } from "react";
import gsap from "gsap";
import "../../components/styles.css";

const GET_USER_URL = "/api/users/user";

function ProfilePage() {
  const [data, setData] = useState([]);
  const [name, setName] = useState("");
  const [major, setMajor] = useState("");
  const [email, setEmail] = useState("");
  useEffect(() => {
    const getData = () => {
      try {
        const mail = localStorage.getItem("userEmail");
        console.log(mail);
        axios
          .get(GET_USER_URL, {
            params: { email: mail },
            headers: { "Content-Type": "application/json" },
          })
          .then((response) => {
            console.log(response.data);
            setData(response.data);
            setName(data.name);
            setMajor(data.major);
            setEmail(data.email);
            console.log(name + " ");
          });
        console.log(data);
      } catch (error) {
        console.log("error");
      }
    };
    getData();
  }, [name, major, email]);

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
    <div className="dashboard">
      <Navbar isLoggedIn={1} navCount={5} />
      <div className="main-container">
        <p className="h2-bold black">My Profile</p>
        <div className="profile-header">
          <img
            className="profile-avatar"
            src="https://cdn3.emoji.gg/emojis/8015-book-hat.png"
          ></img>
          <div className="profile-header-content">
            <div className="profile-header-field">
              <p className="h5-bold black">Full name</p>
              {name ? <p className="h1-bold secondary">{name}</p> : null}
            </div>
            <div className="profile-header-subcontent">
              <div className="profile-header-field">
                <p className="h5-bold black">Email Address</p>
                {email ? <p className="h5-bold secondary">{email}</p> : null}
              </div>
              <div className="profile-header-field">
                <p className="h5-bold black">Major</p>
                {major ? <p className="h5-bold secondary">{major}</p> : null}
              </div>
            </div>

            <div className="profile-header-buttons">
              <div className="profile-cta">
                <p className="h6-bold white">Reset Password</p>
              </div>
              <button className="profile-cta">
                <p className="h6-bold white">Log out</p>
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default ProfilePage;
