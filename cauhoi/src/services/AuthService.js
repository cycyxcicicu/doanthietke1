import axiosInstance from "../instances/axiosInstance";

// Login API
export const login = async (username, password) => {
    console.log("login")
    try {
        const response = await axiosInstance.post(`/auth/token`, 
            { username, password }
        );
  
        if (response.status !== 200) 
            throw new Error(response.data.message || 'Login failed');
  
        const { token, authenticated } = response.data.result;
      
        return { token, authenticated };
    } catch (error) {
        if (error.response) {
            // Lấy thông tin từ phản hồi lỗi
            const { message, code } = error.response.data;
      
            return { message, code };
        }
    }
};
export const logout = async () => {
    try {
        const response = await axiosInstance.post(`/auth/logout`);
        // Bạn có thể trả về một đối tượng với trạng thái thành công
        return { success: true, message: 'Logout successful' };
    } catch (error) {
        // Xử lý lỗi và log ra thông báo lỗi
        console.error('Error during logout:', error.response?.data?.message || error.message);
        return { success: false, message: error.response?.data?.message || error.message };
    }
};
export const verifyToken = async (token) => {
    try {
        const response = await axiosInstance.post(`/auth/introspect`, { token });
        const {valid} = response.data.result;
        return {valid}; // API trả về `true` hoặc `false`
    } catch (error) {
        console.error('Error verifying token:', error);
        return false; // Nếu có lỗi, coi như token không hợp lệ
    }
};