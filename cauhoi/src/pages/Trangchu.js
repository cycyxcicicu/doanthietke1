import React, { useEffect, useRef, useState } from "react";
import { showError } from "../utils/toast";
import { readFile } from "../services/api/FileService";
const Trangchu = () => {
	document.title = 'Trang chủ';
	const fileInputRef = useRef(null);
	const [file, setFile] = useState(undefined);
	const [isDragging, setIsDragging] = useState(false);
	const containerRef = useRef();

	useEffect(() => {
		if (!file) return;
		readFile(file)
			.then(res => {
				setFile(undefined);
				fileInputRef.current.value = '';
				const examId = res;
				if (!examId) return;
				const exam = {
					id: examId,
					name: file.name, 
					time: new Date()
				}

				const jsonStr = localStorage.getItem('uploads');
				if (jsonStr) localStorage.setItem('uploads', JSON.stringify([...JSON.parse(jsonStr), exam]));
				else localStorage.setItem('uploads', JSON.stringify([exam]));
				window.location.href = `/cau-hinh?id=${examId}`;
			})
	}, [file]);

	const allowedTypes = [
		'application/vnd.openxmlformats-officedocument.wordprocessingml.document' // .docx
	];

	const handleDragOver = (e) => {
		e.preventDefault();
		setIsDragging(true);
	};

	const handleDragLeave = () => {
		setIsDragging(false);
	};

	const handleDrop = (e) => {
		e.preventDefault();
		setIsDragging(false);
		const files = e.dataTransfer.files;
		if (files.length > 1) {
			showError('Chỉ cho phép tải lên 1 file');
			return;
		}

		if (!fileValidator(files[0])) {
			return;
		}

		setFile(files[0]);
	};

	const fileValidator = (file) => {
		if (!allowedTypes.includes(file.type)) {
			fileInputRef.current.value = '';
			showError('Chỉ chấp nhận file Word (.docx)');
			return false;
		}

		setFile(file);

		return true;
	}

	const handleFileChange = (e) => {
		const files = e.target.files;
		if (!files || !files[0]) return;

		if (files.length > 1) {
			showError('Chỉ cho phép tải lên 1 file')
			return;
		}

		if (!fileValidator(files[0])) {
			fileInputRef.current.value = '';
			return;
		}

		setFile(files[0]);
	};
	return (
		<>
			<div>
				<div className="text-center pt-12 relative">
					<h1 className="text-3xl font-medium">
						Trộn đề trắc nghiệm Online
					</h1>
					<span>
						Trộn đề Word của bạn thành nhiều đề khác biệt
					</span>
					<br></br>
					<a href="/cau-hinh" className="font-medium mt-3 text-blue-700 hover:text-blue-900 flex items-center justify-center foucs:outline-none">
						<span>Các bản ghi trước đó</span>
						<span className="material-icons text-xl ms-1">east</span>
					</a>
				</div>
			</div>
			<div
				className={`w-full bg-purple-500 flex justify-center items-center flex-col py-10 mt-6 transition ${isDragging ? 'bg-purple-600' : ''}`}
				onDragOver={handleDragOver}
				onDragLeave={handleDragLeave}
				onDrop={handleDrop}
			>
				<input
					type="file"
					ref={fileInputRef}
					className="hidden"
					accept=".docx"
					onChange={handleFileChange}
				/>
				<button
					type="button"
					className="bg-green-600 px-6 py-2 text-white text-xl rounded-lg flex items-center hover:bg-green-700"
					onClick={(e) => { e.stopPropagation(); fileInputRef.current.click() }}
				>
					<span className="material-icons text-white me-2">upload</span>
					<span className="font-medium text-lg">Chọn file</span>
				</button>
				<span className="text-white mt-3 font-medium">Hoặc thả file tại đây</span>
			</div>

			<div className="container">
				<div className="flex flex-col items-center px-4 py-6 pb-12 justify-stre">
					<div className="flex flex-col items-stretch">
						<span className="text-xl font-medium text-center">Hướng dẫn soạn đề gốc của bạn</span>
						<div className="mt-6">
							<div className="border-[2px] border-dashed border-purple-300 bg-purple-200 flex flex-col p-4 rounded">
								<ul className="list-disc pl-6 space-y-2">
									<li>
										Câu hỏi phải bắt đầu bằng chữ <b>"Câu"</b>, ví dụ: Câu 1, Câu 2,... (Hoặc chữ <b>"Question"</b>, ví dụ: Question 1, Question 2,...).
									</li>
									<li>
										Phải xuống dòng (<b>gõ Enter</b>) trước khi gõ các đáp án.
									</li>
									<li>
										Các đáp án bắt buộc phải được bắt đầu bằng các từ <b>"A.", "B.", "C.", "D."</b>. Nếu cần cố định đáp án nào thì ta đặt thêm dấu <strong>#</strong> liền trước, ví dụ: <strong>#A</strong>.
									</li>
									<li>
										Mỗi đáp án nên nằm trên một dòng (không bắt buộc, nhưng Quizizz sẽ nhận dạng chính xác hơn)
									</li>
									<li>
										Đáp án đúng bạn phải tô Đỏ <span className="text-red-500 font-bold">#FF0000</span> (<span className="text-red-500 font-bold">A.</span> hoặc <span className="text-red-500 font-bold">#A.</span>) hoặc gạch chân (<u>A.</u> hoặc <u>#A.</u>)
									</li>
									<li>
										Nếu cần ánh xạ số thứ tự câu hỏi vào trong phần đề bài các câu hỏi thì thay {"{}"} vào mỗi số, hệ thống sẽ tự ánh xạ vào <br></br>
										VD: Đọc đoạn sau và trả lời câu hỏi 10 đến 15 -{">"} {"{}"} đến {"{}"}; 
										Điền vào chỗ trống ..(10).. -{">"} ..({"{}"})..
									</li>
								</ul>
							</div>
						</div>
						<span className="text-lg font-medium text-center mt-6">
							Ngoài ra Quizizz còn hỗ trợ trộn đề theo nhóm câu hỏi, <br /> theo tuỳ chọn trộn bằng cách thêm vào 1 dòng kế trên của từng nhóm, các ký tự dưới đây.
						</span>
						<div className="mt-6">
							<div className="border-[2px] border-dashed border-purple-300 bg-purple-200 flex flex-col p-4 rounded">
								<ul className="list-disc pl-6 space-y-2">
									<li className="list-none">
										<strong>&lt;G0&gt;</strong> <br></br>Nhóm câu hỏi không cần trộn
									</li>
									<li className="list-none">
										<strong>&lt;G1&gt;</strong> <br></br>Nhóm câu hỏi chỉ cần hoán vị các câu hỏi
									</li>
									<li className="list-none">
										<strong>&lt;G2&gt;</strong> <br></br>Nhóm câu hỏi chỉ cần hoán vị các đáp án
									</li>
									<li className="list-none">
										<strong>&lt;G3&gt;</strong> <br></br>Nhóm câu hỏi cần hoán vị cả các câu hỏi và các đáp án
									</li>
									<li className="list-none">
										- Khi cần cố định vị trí nhóm ta thêm dấu <strong>#</strong> liền trước ví dụ: <strong>&lt;#G0&gt;</strong>, <strong>&lt;#G1&gt;</strong>, <strong>&lt;#G2&gt;</strong> hoặc <strong>&lt;#G3&gt;</strong>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div className="absolute left-[50%] top-[100px] translate-x-[-50%] container" id="preview-container">
				<div ref={containerRef}></div>
			</div>
		</>
	);
};

export default Trangchu;
