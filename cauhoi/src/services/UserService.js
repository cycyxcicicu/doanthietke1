import axiosInstance from "../instances/axiosInstance";

export const Taotaikhoan = async(username,password,nickname)=>{
	await axiosInstance.post(`/users/create`, { username, password, nickname });
}