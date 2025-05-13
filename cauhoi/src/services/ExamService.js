import axiosInstance from "../instances/axiosInstance";


export const getExam = async (id) => {
    try {
        const response = await axiosInstance.get(`/exam-free/${id}`);

        return response.data.result;
    } catch {}
};