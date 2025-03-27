import axiosInstance from './instances/axiosInstance';
import axios from 'axios';
import {API_BASE_URL} from './configs/ApiConfig';
axios.defaults.headers.common['Authorization'] = `${getToken()}`;

function getToken() {
	let token = localStorage.getItem('authToken');
	return token ? `Bearer ${token}` : "";
}
// Login API
export const login = async (username, password) => {
    try {
      const response = await  axios.post(`${API_BASE_URL}/auth/token`, {
            username,
            password,
      });
  
      if (response.status !== 200) {
        throw new Error(response.data.message || 'Login failed');
      }
  
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
  export const logout = async (token) => {
      try {
        const response = await axios.post(`${API_BASE_URL}/auth/logout`,{
          token
        });
    // Bạn có thể trả về một đối tượng với trạng thái thành công
        return { success: true, message: 'Logout successful' };
        
        
      } catch (error) {
        // Xử lý lỗi và log ra thông báo lỗi
        console.error('Error during logout:', error.response?.data?.message || error.message);
        return { success: false, message: error.response?.data?.message || error.message };
      }
    };
    export const dangki = async (nickname,username, password) => {
      try {
        const response = await  axios.post(`${API_BASE_URL}/users`, {
          username,
          password,
          nickname
        });
    
        if (response.data.code !== 0) {
          throw new Error(response.data.message || 'Sigin failed');
        }
      } catch (error) {
        console.error('Error during đang kí:', error.response?.data?.message || error.message);
        throw error;
      }
    };
    export const verifyToken = async (token) => {
      try {
        const response = await axios.post(`${API_BASE_URL}/auth/introspect`, { token });
        const{valid}=response.data.result;
        return {valid}; // API trả về `true` hoặc `false`
  
      } catch (error) {
        console.error('Error verifying token:', error);
        return false; // Nếu có lỗi, coi như token không hợp lệ
      }
    };
  export const getClassByUserID= async(userId)=>{
	try{
		const response = await axiosInstance.get(`/class/user/${userId}`)
		const{ListClass}= response.data.result
		return {ListClass}
	}
	catch(error){

	}

  }
  export const Taotaikhoan= async(username,password,nickname)=>{
	try {
		// Gửi request đến API
		const response = await axios.post(`${API_BASE_URL}/users`, { username, password, nickname });
	
		// Kiểm tra xem response và response.data.result có tồn tại không
		if (response && response.data ) {
			const { message, code } = response.data;
		  return { message, code };
		} else {
		  // Trả về thông báo lỗi nếu response không hợp lệ
		  throw new Error("Dữ liệu trả về từ API không hợp lệ");
		}
	  } catch (error) {
		if (error.response) {
			// Lấy thông tin từ phản hồi lỗi
			const { message, code } = error.response.data;
		
			return { message, code };
		  } else if (error.request) {
			// Xử lý nếu không nhận được phản hồi
			console.error('No response received:', error.request);
		  } else {
			// Xử lý các lỗi khác
			console.error('Error during request setup:', error.message);
		  }
	  }

  }
  export const laylaimatkhau = async (username, password) => {
	try {
	  // Gửi yêu cầu PUT
	  const response = await axios.put(`${API_BASE_URL}/users`, {
		username,
		password,
	  });
  
	  // Kiểm tra nếu response và response.data tồn tại và có cấu trúc đúng
	  if (response && response.data) {
		const { message, code } = response.data;
		return { message, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	} catch (error) {
	  // Kiểm tra nếu có lỗi trong phản hồi
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
  };
  export const getclassbyUserId= async(userid)=>{
	try {
	const response = await axiosInstance.get(`/class/user/${userid}`)
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const sualop= async(classid,name,imageclass)=>{
	try {
	const response = await axiosInstance.put(`/class/${classid}`,{name,imageclass})
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const taolop= async(userid,name,imageclass)=>{
	try {
	const response = await axiosInstance.post(`/class/${userid}`,{name: name,imageclass})
	if (response && response.data) {
		const { message, code } = response.data;
		return { message, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const xoalop= async(classid)=>{
	try {
	const response = await axiosInstance.delete(`/class/${classid}`)
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const laybaikiemtra= async(classid)=>{
	try {
	const response = await axiosInstance.get(`/test/${classid}`)
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const laycauhoi= async(cauhoiid)=>{
	try {
	const response = await axiosInstance.get(`/questions/${cauhoiid}`)
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const laylichsu= async(testid,userid)=>{
	try {
	const response = await axiosInstance.get(`/history/${testid}/${userid}`)
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const createlichsu= async(dataArray)=>{
	try {
	const response = await axiosInstance.post(`/history`,dataArray)
	if (response && response.data) {
		const { message, code } = response.data;
		return { message, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const themthanhvien= async(userid,classid)=>{
	try {
	const response = await axiosInstance.post(`/class/${userid}/${classid}`)
	if (response && response.data) {
		const { message, code } = response.data;
		return { message, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const suabaikiemtra = async (testid, quizData) => {
	try {
		console.log(quizData);
	  const response = await axiosInstance.put(`/test/${testid}`, quizData);
	  
	  // Kiểm tra nếu response có dữ liệu
	  if (response && response.data) {
		const { message, code } = response.data;
		return { message, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	} catch (error) {
	  // Xử lý lỗi 400
	  if (error.response) {
		// Kiểm tra nếu mã lỗi là 400
		if (error.response.status === 400) {
		  // Lỗi 400 (Bad Request)
		  console.error("Lỗi 400 - Yêu cầu không hợp lệ:", error.response.data);
		  return { message: "Yêu cầu không hợp lệ", code: 400 };
		}
		
		// Lấy message và code nếu có trong phản hồi lỗi
		const { message, code } = error.response.data || {};
		return { message, code };
	  } else if (error.request) {
		// Xử lý khi không nhận được phản hồi từ server
		console.error("No response received:", error.request);
		throw new Error("Không nhận được phản hồi từ server");
	  } else {
		// Xử lý các lỗi khác (ví dụ lỗi trong setup yêu cầu)
		console.error("Error during request setup:", error.message);
		throw new Error(error.message || "Đã có lỗi xảy ra");
	  }
	}
  };
  
  export const taokiemtra= async(classid,quizData)=>{
	try {
	const response = await axiosInstance.post(`/test/${classid}`,quizData)
	if (response && response.data) {
		const { message, code } = response.data;
		return { message, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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
  export const laybaikiem= async()=>{
	try {
	const response = await axiosInstance.get(`/test`)
	if (response && response.data) {
		const { result, code } = response.data;
		return { result, code };
	  } else {
		throw new Error("Dữ liệu trả về từ API không hợp lệ");
	  }
	}
	catch(error){
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