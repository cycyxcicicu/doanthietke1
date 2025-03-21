import React, { useRef, useState, useEffect } from "react";
import Taocauhoi from "./Taocauhoi";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import { suabaikiemtra, taokiemtra } from "../apiService";

const CreateQuiz = ({ onCloseModal, editingQuiz = null ,classid,refreshData}) => {
	const [quizName, setQuizName] = useState(editingQuiz?.name || "");
	const [quizTime, setQuizTime] = useState(editingQuiz?.thoigian || "");
	const [imagePreview, setImagePreview] = useState(editingQuiz?.imagetest || null);
	const [testid] = useState(editingQuiz?.testid || "");

	const [errors, setErrors] = useState({});
	const questionsListRef = useState(editingQuiz?.questions || []);
	const [questionsList, setQuestionsList] = useState([]);
	const [loading, setLoading] = useState(false);
	const [imageUrl, setImageUrl] = useState(null);
	const [isPublic, setIsPublic] = useState(editingQuiz?.trangthai);
	const handleQuestionsChange = (updatedQuestions) => {
		console.log(updatedQuestions)
		setQuestionsList(updatedQuestions);
	  };
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

	const handleRemoveImage = () => {
		setImagePreview(null);
	};

	const resetForm = () => {
		setQuizTime("");
		setImagePreview(null);
		questionsListRef.current = [];
	};

	const validateForm = () => {
		const newErrors = {};
		if (!quizName.trim()) {
			newErrors.quizName = "Tên bài kiểm tra không được để trống.";
		}
		if (!quizTime || quizTime <= 0) {
			newErrors.quizTime = "Thời gian phải lớn hơn 0.";
		}
		if (!imagePreview) {
			newErrors.imagePreview = "Hãy chọn hình ảnh đại diện.";
		}
		setErrors(newErrors);
		return Object.keys(newErrors).length === 0;
	};
	const parseQuestions = (questions) => {
		return questions.map((question) => ({
			content: question.question,
			choive: question.options.join("|"), // Chuyển `|` thành mảng
			answer: question.correctAnswers.join("|"), // Chuyển `|` thành mảng
		}));
	};
	const handleSubmit = async () => {
		if (!validateForm()) return;

		try {
			console.log(questionsList)
			console.log( questionsListRef[0])

			const quizData = {
				name: quizName,
				thoigian: parseInt(quizTime, 10),
				imagetest: imageUrl ? imageUrl : imagePreview,
				questions:(questionsList.length > 0) ? parseQuestions(questionsList) : questionsListRef[0],
				trangthai: isPublic
			};
			let response;

			if (editingQuiz) {
				response = await suabaikiemtra(testid, quizData);
			  } else {
				response = await taokiemtra(classid, quizData);
			  }
		  
			  // Kiểm tra phản hồi API
			  if (response.code === 1000) {

				toast.success(response.message, {
				  position: "top-right",
				  autoClose: 5000,
				  hideProgressBar: false,
				  closeOnClick: true,
				  pauseOnHover: true,
				  draggable: true,
				});
				
			}
			
			refreshData();
			resetForm();
			onCloseModal();
		} catch (error) {
			console.error(editingQuiz ? "Error updating quiz:" : "Error creating quiz:", error);
			console.log(error)
			toast.error(
				editingQuiz
					? "Đã xảy ra lỗi khi cập nhật bài kiểm tra!"
					: "Đã xảy ra lỗi khi tạo bài kiểm tra!",
				{
					position: "top-right",
					autoClose: 5000,
					hideProgressBar: false,
					closeOnClick: true,
					pauseOnHover: true,
					draggable: true,
				}
			);
		}
	};

	return (
		<div className="h-screen overflow-hidden container mx-auto p-4">


			<div className="flex bg-gray-50 h-[80vh]">
				{/* Form nhập thông tin bài kiểm tra */}
				<div className="w-1/4 bg-white shadow-md rounded-lg p-4 mr-4 flex flex-col h-full">
					<div className="flex-grow">
						<h2 className="text-xl font-bold mb-4">
							{editingQuiz ? "Sửa bài kiểm tra" : "Tạo bài kiểm tra"}
						</h2>
						<div className="mb-4">
							<label className="block text-gray-700 font-medium mb-2">
								Tên bài kiểm tra:
							</label>
							<input
								type="text"
								value={quizName}
								onChange={(e) => setQuizName(e.target.value)}
								placeholder="Nhập tên bài kiểm tra"
								className={`w-full border rounded-lg p-2 ${errors.quizName ? "border-red-500" : ""
									}`}
							/>
							{errors.quizName && (
								<p className="text-red-500 text-sm mt-1">{errors.quizName}</p>
							)}
						</div>
						<div className="mb-4">
							<label className="block text-gray-700 font-medium mb-2">
								Thời gian (phút):
							</label>
							<input
								type="number"
								value={quizTime}
								onChange={(e) => setQuizTime(e.target.value)}
								placeholder="Nhập thời gian làm bài"
								className={`w-full border rounded-lg p-2 ${errors.quizTime ? "border-red-500" : ""
									}`}
							/>
							{errors.quizTime && (
								<p className="text-red-500 text-sm mt-1">{errors.quizTime}</p>
							)}
						</div>
						<div className="mb-4 relative">
							<label className="block text-gray-700 font-medium mb-2">
								Hình ảnh đại diện:
							</label>
							<div
								className={`w-40 h-40 border rounded-lg flex items-center justify-center overflow-hidden relative ${imagePreview ? "" : "border-dashed"
									}`}
							>
								{/* Loading Spinner chỉ hiển thị trong khung ảnh */}
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
									<label className="cursor-pointer text-gray-500 flex items-center justify-center">
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
						<div className="flex items-center mt-4">
                  <input
                    type="checkbox"
                    id="isPublic"
                    checked={isPublic}
                    onChange={() => setIsPublic(!isPublic)}
                    className="mr-2"
                  />
                  <label htmlFor="isPublic" className="text-gray-500">
                    Công khai bài kiểm tra
                  </label>

                </div>

					</div>
					<button
						onClick={handleSubmit}
						className={`w-full py-2 rounded-lg mt-4 transition ${loading
							? "bg-gray-300 text-gray-500 cursor-not-allowed"
							: "bg-purple-500 text-white hover:bg-purple-600"
							}`}
					>
						{editingQuiz ? "Sửa bài kiểm tra" : "Tạo bài kiểm tra"}
					</button>
				</div>
				<div className="w-3/4 bg-gray-50 shadow-md rounded-lg h-full overflow-y-auto">
					<Taocauhoi questionsListRef={questionsListRef} onQuestionsChange={handleQuestionsChange} />
				</div>
			</div>
		</div>
	);
};

export default CreateQuiz;
