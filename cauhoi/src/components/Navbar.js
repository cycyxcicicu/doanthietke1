import React from "react";
import { userId } from '../utils/utils';
const Navbar = () => {
	const user_id = userId();
	return (
		<>
			<div className="bg-purple-500 fixed w-full z-[1000]">
				<div className="container">
					<div className="row flex items-center justify-between p-4">
						{/* Phần trái: Tiêu đề */}
						<div className="text-xl text-white font-bold flex items-center space-x-5">
							<span>Quizizz</span>
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default Navbar;
