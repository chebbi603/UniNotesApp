import React, { useCallback } from "react";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import { IconDownload, IconFilePlus } from "@tabler/icons-react";
import { useDropzone } from "react-dropzone";
import "./notes.css";

function AddNote() {
  const onDrop = useCallback((acceptedFiles) => {
    console.log(acceptedFiles);
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <>
      <Navbar isLoggedIn={1} navCount={0} />
      <div className="main-container">
        <p className="h4-bold black upload-header">Upload a new note</p>

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
                <IconFilePlus size={64} color="#6256CA" />
                <p className="h5-bold secondary">
                  Click here to upload your PDF File
                </p>
              </>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default AddNote;
