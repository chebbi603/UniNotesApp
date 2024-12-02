import react from "react";
import "./dashboard.css";
import Navbar from "../components/Navbar";
import NoteCard from "./NoteCard/NoteCard";
import CourseCard from "./CourseCard";
import Footer from "../components/Footer";

function Dashboard() {
  return (
    <>
      <Navbar isLoggedIn={1} navCount={1} />
      <div className="main-container">
        <div className="dashboard-welcome">
          <img
            className="dashboard-welcome-avatar"
            src="https://scontent-vie1-1.xx.fbcdn.net/v/t39.30808-6/439438068_2589492631231246_1843397142712707744_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=vwUTJuB-MRwQ7kNvgEdxVlz&_nc_zt=23&_nc_ht=scontent-vie1-1.xx&_nc_gid=ADcZWIeZXapIfmcB8ZBsLpc&oh=00_AYAecbVyerzdjkXBUA-Pk-ewL38yL8eAFjbd0tsRjSBwjg&oe=67533A4D"
          ></img>
          <div className="dashboard-welcome-message">
            <p className="h5-bold black">Welcome back</p>
            <p className="h5-bold secondary">Ayoub 👋</p>
          </div>
        </div>
        <div className="dashboard-recent-container">
          <p className="h5-bold black">Continue Reading</p>
          <div className="dashboard-cardlist">
            <NoteCard
              name="Note Fleniya test test"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
          </div>
        </div>
        <div className="dashboard-recent-container">
          <div className="dashboard-section-header">
            <p className="h5-bold black">My Courses</p>
            <a className="h6-bold secondary">View All</a>
          </div>
          <div className="dashboard-cardlist">
            <CourseCard
              name={"Mathematics for Engineers 1"}
              color={"#F4CC0B"}
            />
            <CourseCard
              name={"Mathematics for Engineers 2"}
              color={"#FB369F"}
            />
            <CourseCard
              name={"Software Development For Engineers"}
              color={"#0BA2F4"}
            />
            <CourseCard
              name={"Networks Architecture & Protocols"}
              color={"#0BF445"}
            />
          </div>
        </div>
        <div className="dashboard-recent-container">
          <p className="h5-bold black">Popular Notes</p>
          <div className="dashboard-cardlist">
            <NoteCard
              name="Note Fleniya test test"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
            <NoteCard
              name="Note Fleniya"
              cover={
                "https://th.bing.com/th/id/OIP.IF5bmBfSQAZRoxtcNCwT6wHaE8?rs=1&pid=ImgDetMain"
              }
            />
          </div>
        </div>
      </div>
      <Footer></Footer>
    </>
  );
}

export default Dashboard;
