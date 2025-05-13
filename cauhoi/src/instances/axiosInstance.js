import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { API_BASE_URL } from '../configs/ApiConfig';
import { toast } from "react-toastify";
import { showError } from '../utils/toast';
import { setGlobalLoading } from "../controller/LoadingController";
// Tạo instance của axios
let activeRequests = 0;
const axiosInstance = axios.create({
  baseURL: API_BASE_URL, // Đổi base URL của API theo backend của bạn
  headers: {
    'Content-Type': 'application/json',
  },
});

// Thêm interceptor vào request để kiểm tra và làm mới token nếu cần
axiosInstance.interceptors.request.use(
  async (config) => {
    if (activeRequests++ === 0)
      setGlobalLoading(true);
    console.log("sendrequest")
    const token = localStorage.getItem('authToken');
    if (token ) {
      const tokenExpiration = jwtDecode(token).exp;
      const currentTime = Date.now() / 1000;
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
    } 
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor response: bắt lỗi 401
axiosInstance.interceptors.response.use(
  (response) => {
    setGlobalLoading(false); 
    activeRequests = 0;
    return response
  },
  (error) => {
    setGlobalLoading(false);
    activeRequests = 0;
    console.log("Error:" , error)
    showError(error.response.data.message);
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('authToken');
      console.log(window.location.pathname)
      if (['/dangnhap', '/dangki'].findIndex(item => window.location.pathname.startsWith(item)) === -1)
        window.location.href = '/dangnhap'
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
