import React, { useState, useEffect } from 'react';
import Cardbaikiemtra from '../components/Cardbaikiemtra';  // Import Cardbaikiemtra component
import { ToastContainer } from 'react-toastify';  // Để hiển thị toast thông báo
import { Navigate, useLocation } from 'react-router-dom';
import { laybaikiemtra } from '../apiService';
import { isTokenValid } from '../utils';
import TaobaiKiemtra from './CreateQuiz'

const BaiKiemTraPage = () => {
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState('');
  const location = useLocation(); // Lấy thông tin URL hiện tại
  const queryParams = new URLSearchParams(location.search); // Tạo URLSearchParams từ query string
  const classid = queryParams.get('classid');
  const role = queryParams.get('role');
  	const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  	const [baikiemtra, setBaikiemtra] = useState([]);  // Khai báo state trong component
  	const [editingQuiz, setEditingQuiz] = useState(null); // Trạng thái chỉnh sửa bài kiểm tra
  
	useEffect(() => {
		getbaikiemtra();
		 
		}, []);
		const handleCloseModal = () => {
			// Đóng modal (cập nhật state hoặc chạy hiệu ứng đóng modal)
			setIsEditModalOpen(false);
			setEditingQuiz(null);

			
		};
		const refreshData = () => {
			getbaikiemtra(); // Gọi lại API để làm mới danh sách bài kiểm tra
		  };

const getbaikiemtra = async () => {
		try {
			const { result, code } = await laybaikiemtra(classid);
			if (code === 1000) {
				// Cập nhật dữ liệu lớp học từ API vào state
				setBaikiemtra(result);  // Cập nhật `lophoc` với dữ liệu từ API
			} else {
				// Xử lý khi không có dữ liệu hợp lệ từ API
				console.error("Không có dữ liệu bài kiểm tra.");
			}
		} catch (error) {
			console.error("Lỗi khi gọi API:", error);
		}
	}


  const handleEdit = (baikiemtra) => {
	setIsEditModalOpen(true); // Mở modal
	setEditingQuiz(baikiemtra); // Gán dữ liệu bài kiểm tra vào editingQuiz
	
  };
  const handlecreate = () => {
	setIsEditModalOpen(true); // Mở modal

  };

  const handleDelete = (testId) => {
    // Xử lý khi nhấn vào nút xóa
    console.log('Xóa bài kiểm tra:', testId);
    // Call API xóa bài kiểm tra, sau đó cập nhật lại danh sách
    // toast.success('Xóa bài kiểm tra thành công!');
  };


  return (
    <div className="container mx-auto p-4">
      {/* Thanh tìm kiếm */}
	  <div className="flex justify-between items-center mb-4 bg-purple-100 p-4 rounded">
    <h2 className="text-2xl font-bold text-purple-700">Danh sách bài kiểm tra</h2>
    <input
      type="text"
      className="p-2 border rounded w-1/3"
      placeholder="Tìm kiếm bài kiểm tra"
      value={search}
      onChange={(e) => setSearch(e.target.value)}
    />
    {role === "TEACHER" && (
      <button
        className="bg-purple-500 hover:bg-purple-600 text-white px-4 py-2 rounded"
        onClick={handlecreate} // Dẫn đến trang tạo bài kiểm tra
      >
        Tạo bài kiểm tra
      </button>
    )}
  </div>

      {/* Hiển thị các bài kiểm tra */}
	  
	  {baikiemtra && baikiemtra.length === 0 ? (
        <div className="text-center text-gray-600 mt-8">Không có bài kiểm tra</div>
      ) : (
		<div className="grid grid-cols-3 gap-4">
        {loading ? (
          <div>Đang tải...</div>
        ) : (
			baikiemtra.map((test, index) => (
            <Cardbaikiemtra
              key={index}
			  testid={test.testid}
              name={test.name}
              thoigian={test.thoigian}
              imagetest={test.imagetest}
              role={role}
              link={`/lambaikiemtra?testid=${test.testid}&time=${test.thoigian}`}  // Link đến trang chi tiết bài kiểm tra
              onEdit={() => handleEdit(test)}
              onDelete={() => handleDelete(test.id)}
            />
          ))
        )}
      </div>
      )}
	  {isEditModalOpen && (
		<div className="h-screen flex items-center justify-center bg-black bg-opacity-50 fixed inset-0 z-50">
			<div className="bg-white rounded-lg shadow-lg w-3/4 h-5/6 overflow-hidden">
				<button
					onClick={() => handleCloseModal()} // Đóng modal
					className="absolute top-2 right-2 bg-gray-200 text-gray-600 rounded-full w-8 h-8 flex items-center justify-center hover:bg-gray-300"
				>
					✕
				</button>
				<TaobaiKiemtra
					onCloseModal={handleCloseModal}
					editingQuiz={editingQuiz}
					classid={classid}
					refreshData={refreshData}
				/>
			</div>
		</div>
	)}

      <ToastContainer />
    </div>
  );
};

export default BaiKiemTraPage;
