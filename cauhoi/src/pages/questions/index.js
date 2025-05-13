import React, { useState } from "react";
import dayjs from '../../utils/dayj';

const QuestionIndex = () => {
    // set title cho trang
    document.title = "Ngân hàng câu hỏi";
    const [courses, setCourses] = useState([
        {id: 101, stt: 1, name: 'Toán 12', time_create: '2025-04-24 11:11:11', count_categories: 4, count_questions: 1},
        {id: 102, stt: 2, name: 'Tiếng Anh 12', time_create: '2025-04-23 11:11:11', count_categories: 4, count_questions: 0},
        {id: 103, stt: 3, name: 'Vật Lý 12', time_create: '2025-04-22 11:11:11', count_categories: 4, count_questions: 0},
        {id: 104, stt: 4, name: 'Hóa Học 12', time_create: '2025-04-21 11:11:11', count_categories: 4, count_questions: 0}
    ]);
    return (<>
        <div className="my-6 py-6 container px-2">
            <button type="button" className="bg-purple-500 rounded text-white font-medium flex flex-row items-center justity-center py-2 px-2"
                onClick={() => window.location.href = "/questions/create"}
                >
                <span className='material-icons'>add</span>
                Thêm câu hỏi
            </button>
            <h1 className="text-2xl font-medium mt-2">Ngân hàng câu hỏi</h1>
            <ul className="grid grid-cols-1 items-center sm:grid-cols-2 md:grid-cols-3 mt-6 pt-6">
                {courses.map((item, index) => {
                    return <li className={
                            ((index + 1) % 2 === 0 ? "sm:ms-2 " : "sm:me-2 ") +
                            ((index + 1) % 3 === 2 ? "md:mx-4" : "md:mx-0") + 
                            " text-end text-white mb-4"
                        }>
                        <div className="bg-purple-500 rounded p-4 pb-2">
                            <span className="text-xl font-medium">{item.name}</span> <br></br>
                            <span className="text-sm">{item.count_categories} danh mục | {item.count_questions} câu hỏi</span> <br></br>
                            <span className="text-xs">{dayjs(item.time_create).fromNow()}</span> <br></br>
                            <span className="">
                                <span className="material-icons text-xl hover:text-red-500 cursor-pointer">delete</span>
                                <span className="material-icons text-xl ms-2 hover:text-blue-500 cursor-pointer"
                                    onClick={() => {window.location.href = window.location.href + "/course?id=" + item.id}}>visibility</span>
                            </span>
                        </div>
                    </li>
                })}
            </ul>
        </div>
    </>);
}
export default QuestionIndex;