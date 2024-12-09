import { useEffect, useRef, useState } from "react";
import * as pdfjsLib from "pdfjs-dist";
import axios from "../api/axios";

const PdfThumbnail = ({ fileId, width = 200, height = 150 }) => {
  const canvasRef = useRef(null);

  const [error, setError] = useState(null);
  const [pdfData, setPdfData] = useState(null); // Hold the PDF URL
  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    const fetchPdf = async () => {
      try {
        const response = await axios.get(`/api/notes/${fileId}/view`, {
          headers: { Authorization: `Bearer ${accessToken}` },
          responseType: "arraybuffer",
        });

        const pdfBlob = new Blob([response.data], { type: "application/pdf" });
        const pdfUrl = URL.createObjectURL(pdfBlob);
        setPdfData(pdfUrl);
      } catch (e) {
        console.error("Error fetching the PDF:", e);
        setError("Failed to fetch the PDF file.");
      }
    };

    fetchPdf();
  }, [fileId, accessToken]);

  useEffect(() => {
    const renderThumbnail = async () => {
      if (!pdfData || !canvasRef.current) return;

      try {
        // Load the PDF document
        const pdf = await pdfjsLib.getDocument(pdfData).promise;
        const page = await pdf.getPage(1); // Get the first page of the PDF

        const canvas = canvasRef.current;
        const context = canvas.getContext("2d");

        // Scale the page to fit within the specified width and height
        const viewport = page.getViewport({ scale: 1 });
        const scale = Math.max(
          width / viewport.width,
          height / viewport.height
        ); // Scale to cover the area
        const scaledViewport = page.getViewport({ scale });

        // Set canvas size to match the scaled viewport
        canvas.width = width;
        canvas.height = height;

        // Render the page onto the canvas
        await page.render({
          canvasContext: context,
          viewport: scaledViewport,
        }).promise;
      } catch (e) {
        console.error("Error rendering PDF thumbnail:", e);
        setError("Failed to render PDF thumbnail.");
      }
    };

    renderThumbnail();
  }, [pdfData, width, height]);

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <canvas
      ref={canvasRef}
      style={{
        objectFit: "crop",
        width: `${width}px`,
        height: `${height}px`,
        borderRadius: "16px", // Optional styling for visibility
      }}
    />
  );
};

export default PdfThumbnail;
