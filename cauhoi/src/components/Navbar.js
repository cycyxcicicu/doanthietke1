import React from "react";
import { userId } from '../utils/utils';
const Navbar = () => {
	const user_id = userId();
	return (
		<>
			<div className="bg-purple-500">
				<div className="container">
					<div className="row flex items-center justify-between p-4">
						{/* Phần trái: Tiêu đề */}
						<div className="text-xl text-white font-bold flex items-center space-x-5">
							<span>Quizizz</span>
						</div>
						{!user_id && 
							<button type="button" class="bg-red-600 text-white font-medium px-4 py-2 border-0 rounded hover:bg-red-700 focus:outline-none"
								onClick={() => {window.location.href = '/dangnhap'}}
								>
								Đăng nhập
							</button>
						}
					</div>
				</div>
			</div>
		</>
	);
};

export default Navbar;
