import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import Card from '../components/Card';
import Setting from './SettingsPage';
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import Cardbaikiemtra from "../components/Cardbaikiemtra";
const Trangchu = () => {
	const [isSidebarExpanded, setIsSidebarExpanded] = useState(false); // Trạng thái Sidebar
	const [isEditModalOpen, setIsEditModalOpen] = useState(false);
	const [baikiemtra, setBaikiemtra] = useState([]); 
	const [errors, setErrors] = useState({});
	const handleCloseModal = () => {
		// Đóng modal (cập nhật state hoặc chạy hiệu ứng đóng modal)
		setIsEditModalOpen(false);

		// Gọi callback sau khi đóng modal xong
	};
	const navigate = useNavigate();
	const [activeItem, setActiveItem] = useState("explore"); // Trạng thái mục được chọn
	useEffect(() => {
	}, [activeItem]);
	const renderContent = () => {
		switch (activeItem) {
			case "explore":
				return (
					<>
						{/* Main Content */}
						<div className="p-4">
							<div className="grid grid-cols-4 gap-4">
								{baikiemtra.map((card, index) => {
									console.log(card);
									return (
									<Cardbaikiemtra
										key={index}
										testid={card.test_id}
										name={card.name}
										thoigian={card.thoi_gian}
										imagetest={card.image_test}
										link={`/lambaikiemtra?test_id=${card.test_id}&time=${card.thoi_gian}`}  // Link đến trang chi tiết bài kiểm tra
									
									/>
									)
								})}
							</div>
						</div>
						{isEditModalOpen && (
							<div className="h-screen flex items-center justify-center bg-black bg-opacity-50 fixed inset-0 z-50">
								<div className="bg-white rounded-lg shadow-lg w-3/4 h-5/6 overflow-hidden">
									<button
										onClick={() => handleCloseModal()} // Đóng modal
										className="absolute top-2 right-2 bg-gray-200 text-gray-600 rounded-full w-8 h-8 flex items-center justify-center hover:bg-gray-300"
									>
										✕
									</button>
								</div>
							</div>
						)}
					</>
				);
			case "logout":
				// replace để kiểm soát việc lưu hoặc thay thế trang hiện tại trong lịch sử trình duyệt.
				return <Navigate to="/dangnhap" replace />;
			case "settings":
				return (
					<div className="p-10">
						<Setting />
					</div>
				);
			default:
				return (
					<div className="p-10">
						<h2 className="text-2xl font-bold mb-4">Trang không tồn tại</h2>
					</div>
				);
		}
	};

	return renderContent();
};

export default Trangchu;
