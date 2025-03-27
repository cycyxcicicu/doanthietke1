import React, { useEffect, useState } from 'react';

import { FaEye, FaEyeSlash } from 'react-icons/fa';
import{Taotaikhoan} from '../apiService'
import { toast } from "react-toastify";
import { ToastContainer } from "react-toastify";
import { Link, useNavigate } from 'react-router-dom';
import { isTokenValid } from '../utils/utils';
function Dangki() {
  const [showPassword, setShowPassword] = useState(false);
  const [errorMessages, setErrorMessages] = useState({});
  const navigate = useNavigate(); // Hook dùng để điều hướng trang
  useEffect(() => {
    const checkToken = async () => {
      const valid = await isTokenValid();
      if (valid) {
        navigate('/trangchu'); // Chuyển hướng nếu token hợp lệ
      }
    };

    checkToken();
  }, [navigate]);
  // Hàm validate để kiểm tra dữ liệu đầu vào
  const validateInputs = (inputs) => {
    const errors = {};
    for (const [key, value] of Object.entries(inputs)) {
      if (!value.trim()) {
        errors[key] = `${key === 'nickname' ? 'Nick name' : key === 'username' ? 'Tên tài khoản' : key === 'password' ? 'Mật khẩu' : 'Trường này'} không được để trống`;
      }
    }
    return errors;
  };

  // Hàm xử lý khi người dùng nhấn đăng ký
  const handledangki = async (e) => {
    e.preventDefault();
	setErrorMessages({});
    const nickname = e.target.nickname.value.trim();
    const username = e.target.username.value.trim();
    const password = e.target.password.value.trim();

    // Validate dữ liệu đầu vào
    const errors = validateInputs({ nickname, username, password });
    if (Object.keys(errors).length > 0) {
      setErrorMessages(errors);
      return;
    }

    // Gọi API đăng ký tài khoản
    try {
      const {message, code } = await Taotaikhoan(username, password, nickname);

      if (code === 1000) {
 		navigate('/dangnhap');
        toast.success(message, {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
        });

      } else if (code === 1002) {
        toast.error('Username đã có người sử dụng', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
        });
      }else if (code === 1004) {
        toast.error('Password phải lớn hơn 8 kí tự', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
        });
      }else {
		// Nếu response không hợp lệ, ném lỗi
		throw new Error("Dữ liệu trả về không hợp lệ hoặc thiếu thông tin.");
	  }
    } catch (error) {
      console.error('Đăng ký thất bại:', error);
      toast.error('Có lỗi xảy ra, vui lòng thử lại!', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
      });
    }
  };

  return (
    <div className="min-h-screen bg-purple-900 flex flex-col items-center justify-center">
      {/* Navbar */}
      <header className="w-full flex justify-between items-center px-8 lg:px-80 py-4 text-white fixed top-0 bg-purple-900">
        <h1 className="text-xl font-bold">Quizizz</h1>
        <div className="flex space-x-4">
          <Link to="/dangnhap" className="bg-white text-purple-900 py-1 px-4 rounded">Đăng Nhập</Link>
        </div>
      </header>
	  <ToastContainer />
      {/* Main Container */}
      <div className="bg-white rounded-lg shadow-lg p-8 w-full max-w-lg mx-auto lg:max-w-4xl lg:flex">
        {/* Form Section */}
        <div className="lg:w-1/2 lg:p-8">
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">Welcome to Quizizz</h2>
          
          {/* Registration Form */}
          <form className="space-y-4" onSubmit={handledangki}>
            <div>
              <label className="block text-gray-700">Nick name</label>
              <input
                name="nickname"
                type="text"
                placeholder="Enter your Nick name"
                className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
              />
              {/* Hiển thị lỗi cho trường Nick name */}
              {errorMessages.nickname && (
                <p className="text-red-500 text-sm">{errorMessages.nickname}</p>
              )}
            </div>

            <div>
              <label className="block text-gray-700">Username</label>
              <input
                name="username"
                type="text"
                placeholder="Enter your username"
                className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
              />
              {/* Hiển thị lỗi cho trường Username */}
              {errorMessages.username && (
                <p className="text-red-500 text-sm">{errorMessages.username}</p>
              )}
            </div>

            <div className="relative">
              <label className="block text-gray-700">Password</label>
              <input
                name="password"
                type={showPassword ? 'text' : 'password'}
                placeholder="Enter your password"
                className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
              />
              {/* Hiển thị icon eye để hiển thị/ẩn mật khẩu */}
              <span
                className="absolute top-10 right-3 cursor-pointer text-gray-500"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? <FaEyeSlash /> : <FaEye />}
              </span>
              {/* Hiển thị lỗi cho trường Password */}
              {errorMessages.password && (
                <p className="text-red-500 text-sm">{errorMessages.password}</p>
              )}
            </div>

            <button className="w-full bg-purple-600 text-white py-3 rounded-lg font-semibold hover:bg-purple-700 transition">
              Đăng ký
            </button>
          </form>

          <p className="text-center mt-2">
            Already have an account? <a href="/dangnhap" className="text-purple-600 font-semibold">Đăng Nhập</a>
          </p>
        </div>

        {/* Image Section */}
        <div className="hidden lg:block lg:w-1/2 lg:relative">
          <img
            src="https://img.giaoduc.net.vn/w1000/Uploaded/2024/edxwpcqdh/2021_07_21/cogiao-4136.jpg"
            alt="Welcome"
            className="rounded-r-lg h-full w-full object-cover"
          />
          <div className="absolute bottom-0 left-0 p-4 bg-black bg-opacity-50 text-white">
            <p className="font-semibold">Teachers love us ❤️</p>
            <p>Join over 200 million educators and learners on Quizizz</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dangki;
