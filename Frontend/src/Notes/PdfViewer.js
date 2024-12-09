import React, { useState, useEffect } from "react";
import axios from "axios";
import { Document, Page } from "react-pdf";
import { GlobalWorkerOptions } from "pdfjs-dist";
import { pdfjs } from "react-pdf";
import "./notes.css";
GlobalWorkerOptions.workerSrc = `/unpkg.com/pdfjs-dist@${pdfjs.version}/build/pdf.worker.min.js;`;

function PDFViewer({ fileId }) {
  const [pdfData, setPdfData] = useState(""); // State to hold the PDF file data
  const [pageNumber, setPageNumber] = useState(1);
  const [numPages, setNumPages] = useState(null);
  const accessToken = localStorage.getItem("accessToken");
  useEffect(() => {
    const fetchPdf = () => {
      axios
        .get(`http://localhost:8080/api/notes/${fileId}/view`, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
          responseType: "arraybuffer", // We need to fetch it as an array buffer to display as a PDF
        })
        .then((response) => {
          const pdfBlob = response.data;
          const blob = new Blob([pdfBlob], { type: "application/pdf" });
          const pdfUrl = URL.createObjectURL(blob); // Create a URL for the Blob
          //window.open(pdfUrl, "_blank");
          setPdfData(pdfUrl);
        })
        .catch((error) => {
          console.error("Error fetching the PDF", error);
        });
    };
    fetchPdf();
  }, [fileId]);

  // Function to handle when the PDF document is loaded
  const onLoadSuccess = ({ numPages }) => {
    setNumPages(numPages);
  };

  // Go to the next page
  const nextPage = () => {
    if (pageNumber < numPages) {
      setPageNumber(pageNumber + 1);
    }
  };

  // Go to the previous page
  const prevPage = () => {
    if (pageNumber > 1) {
      setPageNumber(pageNumber - 1);
    }
  };

  return (
    <div className="pdf-container">
      <div className="pdf-controls">
        <p className="h5-bold black">
          Page {pageNumber} of {numPages}
        </p>
        <div className="pdf-buttons">
          <button
            className="pdf-button h6-bold"
            onClick={prevPage}
            disabled={pageNumber === 1}
          >
            Previous
          </button>

          <button
            className="pdf-button h6-bold"
            onClick={nextPage}
            disabled={pageNumber === numPages}
          >
            Next
          </button>
        </div>
      </div>

      <div style={styles.viewerContainer}>
        <Document file={pdfData} onLoadSuccess={onLoadSuccess}>
          <Page pageNumber={pageNumber} width={1320} height={1845} />
        </Document>
      </div>
    </div>
  );
}

const styles = {
  viewerContainer: {
    width: "1320px", // A4 width in pixels
    height: "1845px", // A4 height in pixels
    margin: "0 auto", // Center the container
    border: "1px solid #ccc", // Optional: Add a border for better visual
    overflow: "hidden", // Hide content that goes out of bounds
    textAlign: "center", // Centering the page content
  },
};

export default PDFViewer;
