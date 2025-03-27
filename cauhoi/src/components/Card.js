import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Taobaikiemtra from '../pages/CreateQuiz'
import { FaShareAlt } from 'react-icons/fa';
const Card = ({ name, soluongthanhvien, image_class, class_id,role ,onEdit, onDelete}) => {
	

  return (
    
	<div className="relative bg-white shadow-md rounded-lg overflow-hidden hover:shadow-lg transition-shadow duration-300">
	{/* Ảnh và tiêu đề có liên kết */}
	<Link to={`/baikiemtra?classid=${class_id}&role=${role}`}>
	  <img src={image_class} alt={name} className="w-full h-48 object-cover" />
	</Link>
	<div className="p-4">
	  <Link to={`/baikiemtra?classid=${class_id}&role=${role}`}>
		<h3 className="text-lg font-bold mb-2">{name}</h3>
	  </Link>
	  <p className="text-gray-600">{soluongthanhvien} thành viên </p>
	</div>

	   {/* Kiểm tra nếu role là "teacher" */}
	   {role === 'TEACHER' && (
        <div className="absolute bottom-2 right-2 flex space-x-2">
          {/* Icon sửa */}
          <button
            onClick={onEdit}
            className="text-blue-500 hover:text-blue-600"
          >
            <span className="material-icons">edit</span>
          </button>
          {/* Icon xóa */}
          <button
            onClick={onDelete}
            className="text-red-500 hover:text-red-600"
          >
            <span className="material-icons">delete</span>
          </button>
		  <button className="flex items-center space-x-2 bg-blue-500 text-white px-4 py-2 rounded">
      	<FaShareAlt  />
     
    </button>
        </div>
      )}
  </div>
);

};

export default Card;
