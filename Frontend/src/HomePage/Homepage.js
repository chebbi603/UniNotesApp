import React from "react";
import Navbar from "../components/Navbar";
import "./homepage.css";
import unideblogo from "../assets/notes-logo-nav.svg";
import img2 from "../assets/image-1.png";
import img1 from "../assets/image.png";
import gsap from "gsap";
import { useLayoutEffect } from "react";
import { ScrollTrigger } from "gsap/ScrollTrigger";
import Footer from "../components/Footer";
import { NavLink } from "react-router-dom";

function HomePage() {
  useLayoutEffect(() => {
    let ctx = gsap.context(() => {
      gsap.registerPlugin(ScrollTrigger);

      const showAnim = gsap
        .from(".navbar", {
          yPercent: -100,
          paused: true,
          duration: 0.4,
        })
        .progress(1);
      ScrollTrigger.create({
        start: "1% top",
        end: "max",
        onUpdate: (self) => {
          self.direction === -1 ? showAnim.play() : showAnim.reverse();
        },
      });
      gsap.fromTo(
        ".landing-page",
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
      gsap.to(".landing-page__img1", {
        scrollTrigger: {
          trigger: ".landing-page",
          start: "top 25%",
          end: "bottom",
          scrub: true,
        },
        y: 120,
      });
      gsap.to(".landing-page__img2", {
        scrollTrigger: {
          trigger: ".landing-page",
          start: "top 25%",
          end: "bottom",
          scrub: true,
        },
        y: 160,
      });
    });

    return () => ctx.revert();
  }, []);
  return (
    <>
      <Navbar navCount={1} navButton={1} isLoggedIn={0} />
      <div className="landing-page">
        <img className="landing-page__img1 " src={img1} />
        <div className="landing-page__content">
          {/* Logo */}
          <img src={unideblogo} className="landing-page__logo" />

          {/* Heading */}
          <h1 className="landing-page__headline h2-bold">
            Your <span className="highlight-green">UD Notes</span> ,
            <br />
            More <span className="highlight-purple">Valuable</span> than ever
          </h1>

          {/* Subheading */}
          <p className="landing-page__subheading h6-reg">
            Find top-rated study notes from students taking the same courses as
            you
          </p>

          {/* Call to Action Button */}
          <NavLink to="/login">
            <button className="landing-page__cta h6-bold">
              Join Unideb Notes
            </button>
          </NavLink>

          {/* Learn More Link */}
          <a href="#" className="landing-page__learn-more b1-reg">
            Learn more
          </a>
        </div>

        <img className="landing-page__img2 " src={img2} />
        <div className="landing-page__content">
          <h1 className="landing-page__headline h2-bold">
            Thousands of students One big{" "}
            <span className="highlight-yellow">UD Community</span>
          </h1>

          <p className="landing-page__subheading h6-reg">
            Discover a comprehensive set of notes built by one of the best
            university communities
          </p>

          <div className="landing-stats">
            <div className="landing-stat">
              <p className="h2-bold">25.3K+</p>
              <p className="h6-reg">Students</p>
              <div className="purple-tag">
                <p className="b1-bold">100+ countries</p>
              </div>
            </div>
            <div className="landing-stat">
              <p className="h2-bold">500+</p>
              <p className="h6-reg">Study resources</p>
              <div className="green-tag">
                <p className="b1-bold">English & Hungarian</p>
              </div>
            </div>
            <div className="landing-stat">
              <p className="h2-bold">80+</p>
              <p className="h6-reg">Programs</p>
              <div className="yellow-tag">
                <p className="b1-bold">13 faculties</p>
              </div>
            </div>
          </div>
          <a href="#" className="landing-page__learn-more b1-reg">
            More facts about Unideb
          </a>
        </div>
      </div>

      <Footer />
    </>
  );
}

export default HomePage;
