import axiosInstance from "../../instances/axiosInstance";
import { saveAs } from "file-saver";

export const exportFile = async (exam_id, name, count) => {
  try {
    const response = await axiosInstance.post(
      "/file/export", // Đường dẫn API backend
      {
        exam_id, 
        name, 
        count
      },
      {
        responseType: "blob", // Quan trọng để nhận file nhị phân
      }
    );

    const blob = new Blob([response.data], { type: "application/zip" });
    saveAs(blob, "de-thi.zip");
  } catch (error) {
    console.error("Lỗi khi tải file:", error);
    alert("Không thể tải file đề thi");
  }
};
export const readFile = async (file) => {
    try {
        const formData = new FormData();
        formData.append("file", file);

        const response = await axiosInstance.post(`/file/read`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });

        return response.data.result;
    } catch {}
};
