import axiosInstance from "../../instances/axiosInstance";


export const getExam = async (id) => {
    try {
        const response = await axiosInstance.get(`/exams/${id}`);

        return response.data.result;
    } catch {}
};