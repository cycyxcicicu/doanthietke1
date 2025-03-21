const express = require("express");
const multer = require("multer");
const axios = require("axios");
const FormData = require("form-data");
const cors = require("cors");

const app = express();
app.use(cors()); // Thêm CORS để cho phép từ frontend

const upload = multer();

const IMGUR_CLIENT_ID = "3a1a1c2b22db379"; // Thay bằng Client ID của bạn

app.post("/upload", upload.single("image"), async (req, res) => {
    try {
        const formData = new FormData();
        formData.append("image", req.file.buffer);

        const response = await axios.post("https://api.imgur.com/3/image", formData, {
            headers: {
                Authorization: `Client-ID ${IMGUR_CLIENT_ID}`,
                ...formData.getHeaders(),
            },
        });

        res.json({ link: response.data.data.link }); // Trả về link ảnh
    } catch (error) {
        console.error("Error uploading to Imgur:", error);
        res.status(500).json({ error: "Failed to upload to Imgur" });
    }
});

const PORT = 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
