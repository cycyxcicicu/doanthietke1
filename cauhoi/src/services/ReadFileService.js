import axiosInstance from "../instances/axiosInstance";

export const readFile = async (file) => {
    try {
        const formData = new FormData();
        formData.append("file", file);

        const response = await axiosInstance.post(`/read-file/`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });

        return response.data.result;
    } catch {}
};
