import React from "react";


const Navbar = () => {
	return (
		<div className="flex items-center justify-between bg-purple-100 p-4 pl-16">
			{/* Phần trái: Tiêu đề */}
			<div className="text-xl font-bold flex items-center space-x-5">
				<span>Quizizz</span>
			</div>
		</div>
	);
};

export default Navbar;
