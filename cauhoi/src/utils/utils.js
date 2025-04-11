import { jwtDecode } from 'jwt-decode';
export const getTokenExpiration = (token) => {
  try {
    const decoded = jwtDecode(token);
    return decoded.exp * 1000; // Trả về expiration time (ms)
  } catch (error) {
    console.error('Invalid token', error);
    return null;
  }
};

// Hàm kiểm tra token có hợp lệ không
export const isTokenValid = async () => {
	const token = localStorage.getItem('authToken');
	console.log(token)
	if (!token) {
		return false; // Token không tồn tại
	}

	// Kiểm tra thời gian hết hạn của token
	const expirationTime = getTokenExpiration(token);
	if (!expirationTime) {
		return false; // Token không hợp lệ
	}

	const currentTime = Date.now();
	return currentTime < expirationTime; // Trả về true nếu token chưa hết hạn
};
  
export const userId = () => {
	return localStorage.getItem('userid');
};