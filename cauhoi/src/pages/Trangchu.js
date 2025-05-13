import React, { useEffect, useRef, useState } from "react";
import TronDe from "./exams/uploadFile";
import { userId } from "../utils/utils";
const Trangchu = () => {
	document.title = 'Trang chủ';
	const user_id = userId();
	const [activeTronDe, setActiveTronDe] = useState(false);
	const [title, setTitle] = useState(undefined);
	const [descriptionTitle, setDescriptionTitle] = useState(undefined);
	useEffect(() => {
		if (activeTronDe || !user_id) {
			setTitle("Trộn đề trắc nghiệm Online");
			setDescriptionTitle("Trộn đề Word của bạn thành nhiều đề khác biệt");
		} else {
			setTitle("Tạo đề trắc nghiệm")
		}
	}, [activeTronDe])
	const handerToggleTronDe = () => {
		setActiveTronDe(!activeTronDe);
	}
	return (
		<>
			<div>
				<div className="text-center pt-12 relative">
					{(user_id && activeTronDe) && <button className='text-purple-500 font-medium flex flex-row hover:opacity-75 absolute top-[10px] left-[10px]'
						onClick={handerToggleTronDe}
					>
						<span className='material-icons me-1'>keyboard_backspace</span>
						Trở về
					</button>}
					<h1 className="text-3xl font-medium">
						{title}
					</h1>
					{descriptionTitle && <span>{descriptionTitle}</span>}
				</div>
			</div>
			{(user_id && !activeTronDe) &&
				<div className="container py-12">
					<div className="mx-auto grid max-w-lg grid-cols-1 items-center gap-y-6 sm:gap-y-0 lg:max-w-4xl lg:grid-cols-2">
						<div className="rounded-3xl rounded-t-3xl bg-white/60 p-8 ring-1 ring-gray-900/10 sm:mx-8 sm:rounded-b-none sm:p-10 lg:mx-0 lg:rounded-tr-none lg:rounded-bl-3xl">
							<h3 id="tier-hobby" className="text-base/7 font-semibold text-indigo-600">Trộn đề</h3>
							<p className="text-sm text-base/7 text-gray-600">Trộn đề từ file word gửi lên</p>
							<ul role="list" className="mt-8 space-y-3 text-sm/6 text-gray-600 sm:mt-10">
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-indigo-600" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Trộn được nhiều đề khác nhau từ 1 đề
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-indigo-600" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Trộn câu hỏi
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-indigo-600" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Trộn đáp án
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-indigo-600" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Tiện lợi
								</li>
							</ul>
							<button onClick={handerToggleTronDe} type="button" className="w-[100%] mt-8 block rounded-md px-3.5 py-2.5 text-center text-sm font-semibold text-indigo-600 ring-1 ring-indigo-200 ring-inset hover:ring-indigo-300 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 sm:mt-10">Trộn đề ngay</button>
						</div>
						<div className="relative rounded-3xl bg-purple-500 p-8 shadow-2xl ring-1 ring-gray-900/10 sm:p-10">
							<h3 id="tier-enterprise" className="text-base/7 font-semibold text-white">Tạo đề</h3>
							<p className="text-base/7 text-gray-100 text-sm">Tạo đề từ ngân hàng câu hỏi của bạn</p>
							<ul role="list" className="mt-8 space-y-3 text-sm/6 text-gray-100 sm:mt-10">
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-gray-100" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Tạo được nhiều đề khác nhau theo yêu cầu
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-gray-100" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Ngân hàng câu hỏi được quản lý, dễ dàng chọn lọc
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-gray-100" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Trộn câu hỏi
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-gray-100" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Trộn đáp án
								</li>
								<li className="flex gap-x-3">
									<svg className="h-6 w-5 flex-none text-gray-100" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" data-slot="icon">
										<path d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z"  />
									</svg>
									Dễ dàng thao tác
								</li>
							</ul>
							<button type="button" className="mt-8 block rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-xs hover:bg-indigo-700 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500 sm:mt-10 w-[100%]">Tạo đề ngay</button>
						</div>
					</div>
				</div>
			}
			{(!user_id || activeTronDe) && <TronDe></TronDe>}
		</>
	);
};

export default Trangchu;
