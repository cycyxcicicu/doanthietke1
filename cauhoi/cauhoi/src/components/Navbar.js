import axios from "axios";
import React, { useState } from "react";
import { toast } from "react-toastify";
import { userId } from '../utils';
import { taolop,themthanhvien } from "../apiService";


const Navbar = ({ buttonLabel, modalTitle, modalPlaceholder, modalButtonText, refest }) => {
	const [isModalOpen, setIsModalOpen] = useState(false);
	const [imagePreview, setImagePreview] = useState(null);
	const [className, setClassName] = useState("");
		const [classNameid, setClassNameid] = useState("");

	const [imageUrl, setImageUrl] = useState(null);

	const openModal = () => setIsModalOpen(true);
	const [loading, setLoading] = useState(false);

	const [errors, setErrors] = useState({});

	const handleRemoveImage = () => {
		setImagePreview(null);
	};
	const taolop11 = async () => {
		const newErrors = {};
		if (!className.trim()) {
			newErrors.className = "không được để tên lớp trống.";
		}
		if (!imagePreview) {
			newErrors.imagePreview = "Hãy chọn hình ảnh đại diện.";
		}
		if (newErrors.className || newErrors.imagePreview) {
			setErrors(newErrors);
			return; // Dừng lại nếu có lỗi
		}
		try {

			const { message, code } = await taolop(userId(), className, imagePreview);
			console.log({ message, code });
			if (code === 1000) {

				toast.success(message, {
					position: 'top-right',
					autoClose: 5000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
				});
				refest()
			}
			else if (code === 1004) {
				toast.error(message, {
					position: 'top-right',
					autoClose: 5000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
				});
			}
		} catch (error) {
			console.error('tạo thất bại:', error);
			toast.error('Có lỗi xảy ra, vui lòng thử lại!', {
				position: 'top-right',
				autoClose: 5000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
			});
		}
		handleCloseModal();
		resetForm();
	};

	const handleCloseModal = () => {
		// Đóng modal (cập nhật state hoặc chạy hiệu ứng đóng modal)
		setIsModalOpen(false);

		// Gọi callback sau khi đóng modal xong
	};
	const resetForm = () => {
		setClassName("");
		setClassNameid("")
		setImagePreview(null)
	};
	const thamgia=async()=>{
		try {

			const { message, code } = await themthanhvien(userId(), classNameid);
			console.log({ message, code });
			if (code === 1000) {
				toast.success(message, {
					position: 'top-right',
					autoClose: 5000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
				});

			} else if (code === 1001){
				toast.error(message, {
					position: 'top-right',
					autoClose: 5000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
				});
			}
		
			}
		 catch (error) {
			console.error('tạo thất bại:', error);
			toast.error('Có lỗi xảy ra, vui lòng thử lại!', {
				position: 'top-right',
				autoClose: 5000,
				hideProgressBar: false,
				closeOnClick: true,
				pauseOnHover: true,
				draggable: true,
			});
			
		}
		handleCloseModal();
		resetForm()
	}
	const handleImageUpload = async (e) => {
		const file = e.target.files[0];
		if (!file) return; // Nếu không có file, thoát hàm

		try {
			setLoading(true); // Bật trạng thái loading
			// Tải ảnh lên server
			const formData = new FormData();
			formData.append("image", file);
			const response = await axios.post("http://localhost:5000/upload", formData);

			// Lưu link ảnh từ server
			const imageUrl = response.data.link;
			setImageUrl(imageUrl)
			setImagePreview(imageUrl); // Gán link ảnh từ server làm preview (nếu cần)
		} catch (error) {
			console.error("Error uploading image:", error);
			alert("Đã xảy ra lỗi khi tải ảnh lên");
		} finally {
			setLoading(false); // Tắt trạng thái loading
		}
	};

	// Hàm đóng modal
	const closeModal = () => setIsModalOpen(false);


	// Gửi dữ liệu lớp học
	const handleSubmit = () => {
		taolop11();
	};
	const handleButtonClick = () => {
		if (modalButtonText !== "Join") {
			handleSubmit();
		} else {
			thamgia();
			console.log("Join button clicked");
		}
	};
	return (
		<div className="flex items-center justify-between bg-purple-100 p-4 pl-16">
			{/* Phần trái: Tiêu đề */}
			<div className="text-xl font-bold flex items-center space-x-5">
				<span>Quizizz</span>
			</div>

			{/* Thanh tìm kiếm */}
			<div className="flex items-center bg-white shadow-md rounded-full px-4 py-2 w-full max-w-lg mx-auto">
				<span className="material-icons text-gray-400">search</span>
				<input
					type="text"
					placeholder={`Tìm kiếm trong ${buttonLabel === "Tạo lớp học" ? "lớp học" : "chủ đề"}`}
					className="flex-1 outline-none px-4 text-sm text-gray-600"
				/>
			</div>

			{/* Nút mở modal */}
			<button
				onClick={openModal}
				className="bg-purple-500 text-white px-4 py-2 rounded hover:bg-purple-600"
			>
				{buttonLabel}
			</button>

			{/* Modal */}
			{isModalOpen && (
				<>
					{/* Lớp nền tối */}
					<div className="fixed inset-0 bg-black bg-opacity-50 z-[50]"></div>

					{/* Nội dung modal */}
					<div className="fixed inset-0 flex justify-center items-center z-[60]">
						<div
							className="bg-gradient-to-b from-purple-700 to-purple-900 p-8 rounded-xl relative shadow-lg flex flex-col items-center pointer-events-auto"
						>
							{/* Nút X để đóng modal */}
							<button
								className="absolute top-2 right-2 text-white bg-transparent rounded-full w-8 h-8 flex items-center justify-center hover:text-red-600 hover:scale-110 transition-all duration-200"
								onClick={closeModal}
							>
								✕
							</button>

							{
								modalButtonText !== "Join" ? (
									<>
										<h3 className="text-white text-2xl font-bold mb-4">{modalTitle}</h3>
										<div className="flex items-center bg-white rounded-full px-4 py-2 shadow-md w-full max-w-md">
											<input
												type="text"
												placeholder={modalPlaceholder}
												className="flex-1 outline-none px-2 text-gray-600"
												onChange={(e) => setClassName(e.target.value)}
											/>
											<button
												className="bg-purple-500 text-white px-4 py-2 rounded-full hover:bg-purple-600 ml-2"
												onClick={() => handleButtonClick()}
											>
												Tạo Lớp
											</button>
										</div>
										{errors.className && (
											<p className="text-red-500 text-sm mt-1">{errors.className}</p>
										)}
										<div className="relative w-full max-w-md mb-4 top-5 right-5">
											<div
												className={`w-96 h-96 border rounded-lg flex items left-6 justify-center overflow-hidden relative ${imagePreview ? "" : "border-dashed"}`}
											>
												{loading && (
													<div className="absolute inset-0 flex items-center justify-center bg-gray-100 z-10">
														<div className="spinner border-t-4 border-purple-500 border-solid rounded-full w-10 h-10 animate-spin"></div>
													</div>
												)}
												{imagePreview && !loading ? (
													<>
														<img
															src={imagePreview}
															alt="Hình đại diện"
															className="object-cover w-full h-full"
														/>
														<button
															onClick={handleRemoveImage}
															className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center text-white text-2xl rounded-lg opacity-0 hover:opacity-100 transition"
														>
															🗑
														</button>
													</>
												) : !loading ? (
													<label className="cursor-pointer text-gray-500 flex items-center justify-center w-full h-full">
														<span>+ Thêm ảnh</span>
														<input
															type="file"
															accept="image/*"
															onChange={handleImageUpload}
															className="hidden"
														/>
													</label>
												) : null}
											</div>
											{errors.imagePreview && (
												<p className="text-red-500 text-sm mt-1">{errors.imagePreview}</p>
											)}
										</div>
									</>
								) : (
									<>
										<h3 className="text-white text-2xl font-bold mb-4">Nhập mã lớp học</h3>
										<div className="flex items-center bg-white rounded-full px-4 py-2 shadow-md w-full max-w-md">
											<input
												type="text"
												placeholder="Nhập mã lớp học"
												onChange={(e) => setClassNameid(e.target.value)}

												className="flex-1 outline-none px-2 text-gray-600"
											/>
											<button
												className="bg-purple-500 text-white px-4 py-2 rounded-full hover:bg-purple-600 ml-2"
												onClick={() => handleButtonClick()}
											>
											
												Tham gia
											</button>
										</div>
									</>
								)
							}


						</div>
					</div>
				</>
			)}
		</div>
	);
};

export default Navbar;
