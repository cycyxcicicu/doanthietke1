import axiosInstance from "../instances/axiosInstance";

export const getMyClass = async () => {
    try {
        const response = await axiosInstance.get(`/class/my-class`)
        if (response && response.data) {
            const { result, code } = response.data;
            return { result, code };
        } else {
            throw new Error("Dữ liệu trả về từ API không hợp lệ");
        }
    }
    catch (error) {
        if (error.response) {
            const { result, code } = error.response.data || {}; // Cố gắng lấy message và code nếu có
            return { result, code };
        } else if (error.request) {
            // Xử lý nếu không nhận được phản hồi từ server
            console.error("No response received:", error.request);
            throw new Error("Không nhận được phản hồi từ server");
        } else {
            // Xử lý các lỗi khác
            console.error("Error during request setup:", error.message);
            throw new Error(error.message || "Đã có lỗi xảy ra");
        }
    }
}
export const sualop = async (classid, name, imageclass) => {
    try {
        const response = await axiosInstance.put(`/class/${classid}`, { name, image_class: imageclass })
        if (response && response.data) {
            const { result, code } = response.data;
            return { result, code };
        } else {
            throw new Error("Dữ liệu trả về từ API không hợp lệ");
        }
    }
    catch (error) {
        if (error.response) {
            const { result, code } = error.response.data || {}; // Cố gắng lấy message và code nếu có
            return { result, code };
        } else if (error.request) {
            // Xử lý nếu không nhận được phản hồi từ server
            console.error("No response received:", error.request);
            throw new Error("Không nhận được phản hồi từ server");
        } else {
            // Xử lý các lỗi khác
            console.error("Error during request setup:", error.message);
            throw new Error(error.message || "Đã có lỗi xảy ra");
        }
    }
}
export const taolop = async (userid, name, imageclass) => {
    try {
        const response = await axiosInstance.post(`/class/${userid}`, { name, image_class: imageclass })
        if (response && response.data) {
            const { message, code } = response.data;
            return { message, code };
        } else {
            throw new Error("Dữ liệu trả về từ API không hợp lệ");
        }
    }
    catch (error) {
        if (error.response) {
            const { message, code } = error.response.data || {}; // Cố gắng lấy message và code nếu có
            return { message, code };
        } else if (error.request) {
            // Xử lý nếu không nhận được phản hồi từ server
            console.error("No response received:", error.request);
            throw new Error("Không nhận được phản hồi từ server");
        } else {
            // Xử lý các lỗi khác
            console.error("Error during request setup:", error.message);
            throw new Error(error.message || "Đã có lỗi xảy ra");
        }
    }
}
export const xoalop = async (classid) => {
    try {
        const response = await axiosInstance.delete(`/class/${classid}`)
        if (response && response.data) {
            const { result, code } = response.data;
            return { result, code };
        } else {
            throw new Error("Dữ liệu trả về từ API không hợp lệ");
        }
    }
    catch (error) {
        if (error.response) {
            const { result, code } = error.response.data || {}; // Cố gắng lấy message và code nếu có
            return { result, code };
        } else if (error.request) {
            // Xử lý nếu không nhận được phản hồi từ server
            console.error("No response received:", error.request);
            throw new Error("Không nhận được phản hồi từ server");
        } else {
            // Xử lý các lỗi khác
            console.error("Error during request setup:", error.message);
            throw new Error(error.message || "Đã có lỗi xảy ra");
        }
    }
}
export const themthanhvien = async (userid, classid) => {
    try {
        const response = await axiosInstance.post(`/class/${userid}/${classid}`)
        if (response && response.data) {
            const { message, code } = response.data;
            return { message, code };
        } else {
            throw new Error("Dữ liệu trả về từ API không hợp lệ");
        }
    }
    catch (error) {
        if (error.response) {
            const { message, code } = error.response.data || {}; // Cố gắng lấy message và code nếu có
            return { message, code };
        } else if (error.request) {
            // Xử lý nếu không nhận được phản hồi từ server
            console.error("No response received:", error.request);
            throw new Error("Không nhận được phản hồi từ server");
        } else {
            // Xử lý các lỗi khác
            console.error("Error during request setup:", error.message);
            throw new Error(error.message || "Đã có lỗi xảy ra");
        }
    }

}