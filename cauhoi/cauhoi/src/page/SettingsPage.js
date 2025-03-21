import React, { useState } from "react";

const SettingsPage = () => {
  const [email, setEmail] = useState("heleo");
  

  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleSaveChanges = () => {
    console.log("Lưu thay đổi:", { email });
  };

  const handleUpdatePassword = () => {
    if (password === confirmPassword) {
      console.log("Mật khẩu được cập nhật:", password);
    } else {
      console.log("Mật khẩu không khớp!");
    }
  };



  return (
    <div className="p-8 max-w-2xl mx-auto bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-bold mb-8">Cài đặt</h2>

      {/* Thông tin tài khoản */}
      <div className="mb-8">
        <h3 className="text-lg font-bold mb-2">Tài khoản</h3>
        <label className="block text-sm font-medium mb-1">Biệt danh</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full px-4 py-2 border rounded mb-4"
        />
       
        <button
          onClick={handleSaveChanges}
          className="bg-purple-500 text-white px-4 py-2 rounded hover:bg-purple-600"
        >
          Lưu thay đổi
        </button>
      </div>

  

      {/* Mật khẩu */}
      <div className="mb-8">
        <h3 className="text-lg font-bold mb-2">Mật khẩu</h3>
        <label className="block text-sm font-medium mb-1">Mật khẩu mới</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Mật khẩu mới (ít nhất 6 ký tự)"
          className="w-full px-4 py-2 border rounded mb-4"
        />
        <label className="block text-sm font-medium mb-1">
          Nhập lại mật khẩu mới
        </label>
        <input
          type="password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          placeholder="Nhập mật khẩu mới lần nữa"
          className="w-full px-4 py-2 border rounded mb-4"
        />
        <button
          onClick={handleUpdatePassword}
          className="bg-purple-500 text-white px-4 py-2 rounded hover:bg-purple-600"
        >
          Cập nhật mật khẩu
        </button>
      </div>

     
    </div>
  );
};

export default SettingsPage;
