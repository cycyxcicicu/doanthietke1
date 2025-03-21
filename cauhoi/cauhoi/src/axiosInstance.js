import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

// Tạo instance của axios
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/cauhoi', // Đổi base URL của API theo backend của bạn
  headers: {
    'Content-Type': 'application/json',
  },
});

// Thêm interceptor vào request để kiểm tra và làm mới token nếu cần
axiosInstance.interceptors.request.use(
  async (config) => {
    const token = localStorage.getItem('authToken');
    const tokenExpiration = jwtDecode(token).exp;
    if (token ) {
      const currentTime = Date.now()/1000;
      const timeRemaining = tokenExpiration - currentTime;

      // Kiểm tra nếu token hết hạn
      if (timeRemaining <= 0) {
        console.error('Token expired. Redirecting to login.');
        localStorage.removeItem('authToken');
		localStorage.removeItem('userid');

        window.location.href = '/dangnhap'; // Chuyển hướng đến trang đăng nhập
        return Promise.reject('Token expired');
      }

      // Làm mới token nếu còn < 10 phút
      if (timeRemaining < 10 * 60 ) {
        try {
          const response = await axiosInstance.post('/auth/refresh', { token });

          if (response.data.token) {
            const newToken = response.data.token;

            localStorage.setItem('authToken', newToken);
            localStorage.setItem('userid', jwtDecode(newToken).userid);
            config.headers['Authorization'] = `Bearer ${newToken}`;
          }
        } catch (error) {
          console.error('Token refresh failed', error);
          localStorage.removeItem('authToken');
          localStorage.removeItem('userid');
          window.location.href = '/dangnhap'; // Chuyển hướng đến trang đăng nhập
          return Promise.reject('Token refresh failed');
        }
      } else {
        config.headers['Authorization'] = `Bearer ${token}`;
      }
    } else {
      // Không có token, chuyển hướng đến trang đăng nhập
      console.error('No token found. Redirecting to login.');
      window.location.href = '/dangnhap'; // Chuyển hướng đến trang đăng nhập
      return Promise.reject('No token found');
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;
