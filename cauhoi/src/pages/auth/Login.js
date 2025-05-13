import React, { useState ,useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { login } from '../../services/AuthService';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { jwtDecode } from 'jwt-decode';
import { isTokenValid } from '../../utils/utils';
import { toast } from 'react-toastify';
function Login() {
  // set title cho trang
  document.title = "Đăng nhập";
  const [showResetPassword, setShowResetPassword] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showPassword1, setShowPassword1] = useState(false);
  const [showPassword2, setShowPassword2] = useState(false);
  const [errorMessages, setErrorMessages] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const checkToken = async () => {
      const valid = await isTokenValid();
      if (valid) {
        navigate('/trangchu'); // Chuyển hướng nếu token hợp lệ
      }
    };

    checkToken();
  }, [navigate]);
  const quaylaitrangdangnhap = () => {
    setErrorMessages({});
    setShowResetPassword(false)
  }
  const validateInputs = (inputs) => {
    const errors = {};
    for (const [key, value] of Object.entries(inputs)) {
      if (!value.trim()) {
        errors[key] = `${key === 'username' ? 'Tên tài khoản' : key === 'password' ? 'Mật khẩu' : 'Trường này'} không được để trống`;
      }
    }
    return errors;
  };
 

  const handleLogin = async (e) => {
    e.preventDefault();
    const username = e.target.username.value;
    const password = e.target.password.value;

    const errors = validateInputs({ username, password });
    if (Object.keys(errors).length > 0) {
      setErrorMessages(errors);
      return;
    }
    try {
      const { token } = await login(username, password);   
      toast.success("Đăng nhập thành công", {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
      });
      console.log(token)
      const decodedToken = jwtDecode(token);
      
      localStorage.setItem('authToken', token);
      localStorage.setItem('userid', decodedToken.userid);
      navigate('/trangchu', { replace: true });
    } catch {}
  };

  const handleResetPassword = async (e) => {
	setErrorMessages({});
    e.preventDefault();
    const username = e.target.username.value;
    const newPassword = e.target.newPassword.value;
    const confirmPassword = e.target.confirmPassword.value;

    const errors = validateInputs({ username, newPassword, confirmPassword });
    if (Object.keys(errors).length > 0) {
      setErrorMessages(errors);
      return;
    }

    if (newPassword !== confirmPassword) {
      setErrorMessages({ confirmPassword: 'Mật khẩu xác nhận không khớp' });
      return;
    }
  };

  return (
    <div className="min-h-screen bg-purple-900 flex flex-col items-center justify-center">
      <header className="w-full flex justify-between items-center px-8 lg:px-80 py-4 text-white fixed top-0 bg-purple-900">
        <h1 className="text-xl font-bold">Quizizz</h1>
        <div className="flex space-x-4">
          <Link to="/dangki" className="bg-white text-purple-900 py-1 px-4 rounded">Đăng Kí</Link>
        </div>
      </header>
      <div className="bg-white rounded-lg shadow-lg p-8 w-full max-w-lg mx-auto lg:max-w-4xl lg:flex">
        <div className="lg:w-1/2 lg:p-8">
          <button className='text-black flex flex-row hover:opacity-75'
            onClick={() => {window.location.href = "/"}}
            >
            <span className='material-icons'>keyboard_backspace</span>
          </button>
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">Log in to Quizizz</h2>

          {!showResetPassword && (
            <form className="space-y-4" onSubmit={handleLogin}>
              <div>
                <label className="block text-gray-700">Username</label>
                <input
                  name="username"
                  type="text"
                  placeholder="Enter your username"
                  className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
                />
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
                <span
                  className="absolute top-10 right-3 cursor-pointer text-gray-500"
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <FaEyeSlash /> : <FaEye />}
                </span>
                {errorMessages.password && (
                  <p className="text-red-500 text-sm">{errorMessages.password}</p>
                )}
              </div>
              <button className="w-full bg-purple-600 text-white py-3 rounded-lg font-semibold hover:bg-purple-700 transition">
                Đăng Nhập
              </button>
            </form>
          )}

          {showResetPassword && (
            <form className="space-y-4" onSubmit={handleResetPassword}>
              <div>
                <label className="block text-gray-700">Username</label>
                <input
                  name="username"
                  type="text"
                  placeholder="Enter your username"
                  className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
                />
                {errorMessages.username && (
                  <p className="text-red-500 text-sm">{errorMessages.username}</p>
                )}
              </div>
              <div className="relative">
                <label className="block text-gray-700">New Password</label>
                <input
                  name="newPassword"
                  type={showPassword1 ? 'text' : 'password'}
                  placeholder="Enter new password"
                  className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
                />   <span
                  className="absolute top-10 right-3 cursor-pointer text-gray-500"
                  onClick={() => setShowPassword1(!showPassword1)}
                >
                  {showPassword1 ? <FaEyeSlash /> : <FaEye />}
                </span>
                {errorMessages.newPassword && (
                  <p className="text-red-500 text-sm">{errorMessages.newPassword}</p>
                )}
              </div>
              <div className="relative">
                <label className="block text-gray-700">Confirm New Password</label>
                <input
                  name="confirmPassword"
                  type={showPassword2 ? 'text' : 'password'}
                  placeholder="Confirm new password"
                  className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-600"
                />
				        <span
                  className="absolute top-10 right-3 cursor-pointer text-gray-500"
                  onClick={() => setShowPassword2(!showPassword2)}
                >
                  {showPassword2 ? <FaEyeSlash /> : <FaEye />}
                </span>
                {errorMessages.confirmPassword && (
                  <p className="text-red-500 text-sm">{errorMessages.confirmPassword}</p>
                )}
              </div>
              <button className="w-full bg-purple-600 text-white py-3 rounded-lg font-semibold hover:bg-purple-700 transition">
                Đặt lại mật khẩu
              </button>
              <button
                type="button"
                onClick={() => quaylaitrangdangnhap()}
                className="text-gray-600 font-semibold mt-2 block text-right"
              >
                Quay lại đăng nhập
              </button>
            </form>
          )}
		  
        </div><div className="hidden lg:block lg:w-1/2 lg:relative">
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

export default Login;
