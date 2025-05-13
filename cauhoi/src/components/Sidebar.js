import React from "react";
import { logout } from "../services/AuthService";
import { useNavigate } from "react-router-dom";
import { userId } from '../utils/utils';
const Sidebar = ({ isExpanded, toggleSidebar }) => {
  const user_id = userId();
  const pathname = window.location.pathname;
  const menuItems = [
    { id: "questions", label: "Ngân hàng câu hỏi", icon: "segment" },
    { id: "logout", label: "Đăng xuất", icon: "logout" },
    { id: "setting", label: "Cài đặt", icon: "settings" },
  ];
  const navigate = useNavigate();
  const handleLogout = async () => {
    const { success, message } = await logout();  // Gọi API logout từ api.js
    if (success) {
      console.log(message);  // Hiển thị thông báo nếu cần
      localStorage.removeItem('authToken');
      navigate("/dangnhap", { replace: true });  // Chuyển hướng đến trang đăng nhập
    } else {
      console.error('Logout failed:', message);  // Nếu có lỗi, log thông báo lỗi
    }
  };
  return (
    user_id && (
      <div
        className={
          `fixed top-0 left-0 h-screen bg-purple-50 shadow-lg 
          ${isExpanded ? "w-64" : "w-16"} 
          transition-all duration-300 flex flex-col`
        }
        onMouseEnter={() => toggleSidebar(true)}
        onMouseLeave={() => toggleSidebar(false)}
      >
        {/* Logo */}
        <div className="my-4 flex justify-center flex-col items-center">
          <img src="/image/meo.jpg" alt="Logo" className="w-12 h-12" />
          {isExpanded && <p className="mt-2 font-medium text-purple-500 max-w-[80%] truncate">{user_id}</p>}
        </div>

        {/* Nút Tạo mới */}
        <div className="flex flex-col items-center w-full gap-4">
          <button className="bg-purple-500 text-white px-4 py-4 rounded-lg shadow-lg hover:bg-purple-600 transition-all flex items-center justify-center w-full focus:outline-none"
            onClick={() => { window.location.href = '/' }}
          >
            <span className="material-icons text-white">add_circle</span>
            <span
              className={`ml-4 text-sm font-medium ${isExpanded ? "inline-block" : "hidden"
                }`}
            >
              Tạo mới
            </span>
          </button>
        </div>

        {/* Các nút danh mục */}
        <div className="flex flex-col w-full gap-4">
          {menuItems.map((item) => (
            item.id === "logout" ? (
              // Nếu là "Đăng xuất", tạo nút xử lý đăng xuất
              <button
                key={item.id}
                className={`w-full flex items-center p-2 rounded-lg hover:bg-purple-100 text-purple-500`}
                onClick={handleLogout}  // Gọi hàm handleLogout khi bấm "Đăng xuất"
              >
                <div
                  className={`flex items-center ${isExpanded ? "justify-start pl-6" : "justify-center"
                    } w-full`}
                >
                  <span className="material-icons">{item.icon}</span>
                  <span
                    className={`ml-2 text-sm ${isExpanded ? "inline-block" : "hidden"}`}
                  >
                    {item.label}
                  </span>
                </div>
              </button>
            ) : (
              <button
                key={item.id}
                className={`w-full flex items-center p-2 rounded-lg ${pathname.startsWith('/' + item.id)
                    ? "bg-purple-200 text-purple-800 font-bold"
                    : "hover:bg-purple-100 text-purple-500"
                  }`}
                onClick={() => { window.location.href = '/' + item.id }}
              >
                <div
                  className={`flex items-center ${isExpanded ? "justify-start pl-6" : "justify-center"
                    } w-full`}
                >
                  <span className="material-icons">{item.icon}</span>
                  <span
                    className={`ml-2 text-sm ${isExpanded ? "inline-block" : "hidden"}`}
                  >
                    {item.label}
                  </span>
                </div>
              </button>
            )
          ))}
        </div>
      </div>
    )
  );
};

export default Sidebar;
