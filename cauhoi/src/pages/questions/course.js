import React, { useState } from "react";
import { useSearchParams } from 'react-router-dom';
import {getFirstAnswer} from '../../utils/utils';

const Course = () => {
    document.title = "Ngân hàng câu hỏi";
    const [searchParams] = useSearchParams();
    const course_id = searchParams.get('id'); // 👈 lấy giá trị của "id"
    const [openCategories, setOpenCategories] = useState([]);
    const [course, setCourse] = useState({id: 101, stt: 1, name: 'Toán 12', time_create: '2025-04-24 11:11:11', categories: [
        {id: 201, stt: 1, name: "Chương 1", time_create: '2024-11-10 11:11:11', questions: [
            {
                id: 301, 
                content: `Xác định thành ngữ trong đoạn văn sau: “Lí Thông lân la gợi chuyện, rồi gạ cùng Thạch Sanh kết nghĩa anh em. Sớm mồ côi cha mẹ, tứ cố vô thân, nay có người săn sóc đến mình, Thạch Sanh cảm động, vui vẻ nhận lời (Thạch Sanh)`, 
                answers: [{content: 'Kết nghĩa anh em.'}, {content: 'Mồ côi cha mẹ.'}, {content: 'Tứ cố vô thân.', is_correct: true}, {content: 'Đoạn văn trên không có thành ngữ.'}], 
                level: "EASY", 
                images: []
            }
        ]},
        {id: 202, stt: 1, name: "Chương 2 abcud dchd dhfhd  dhdh dfhd đùb udud udfdbd dhhđ uedu ufe dufdufdb feuufe dufuedb ùeue ufyryrf fyrryf feufe ufey efeufe uefu uefufue ueueufee edeededehjh huuefheej jehffje fejehh fhehjfe ehfhee", time_create: '2024-11-10 11:11:11'},
        {id: 203, stt: 1, name: "Chương 3", time_create: '2024-11-10 11:11:11'},
        {id: 204, stt: 1, name: "Chương 4", time_create: '2024-11-10 11:11:11'}
    ]});
    const toggleCategory = (id) => {
        if (openCategories.findIndex(item => item.id === id) !== -1)
            setOpenCategories(openCategories.filter(item => item.id !== id))
        else 
            setOpenCategories([...openCategories, {id: id, openQuestions: []}])
    }
    const toggleQuestion = (categoryId, questionId) => {
        const categoryIndex = openCategories ? openCategories.findIndex(item => item.id === categoryId) : -1;
        if (categoryIndex === -1) return;
        let category = openCategories[categoryIndex];
        if (category.openQuestions && category.openQuestions.findIndex(item => item === questionId) === -1)
            category.openQuestions = [...category.openQuestions, questionId];
        else 
            category.openQuestions = category.openQuestions.filter(item => item !== questionId);
        console.log(category)
        setOpenCategories([...openCategories.filter(item => item.id !== categoryId), category]);
    }
    return (<>
        <div className="my-6 py-6 container px-2">
            <button type="button" className="bg-purple-500 rounded text-white font-medium flex flex-row items-center justity-center py-2 px-2"
                onClick={() => window.location.href = "/questions/create?coure_id=" + course_id}
                >
                <span className='material-icons'>add</span>
                Thêm câu hỏi
            </button>
            <h1 className="text-2xl font-medium mt-2">Ngân hàng câu hỏi</h1>
            <h1 className="text-xl font-medium mt-2">Môn học: {course.name}</h1>
            <ul className={"mt-6 pt-6 w-[100%]"}>
                {course.categories.map(category => {
                    const findIndexCate = openCategories ? openCategories.findIndex(item => item.id === category.id) : -1; 
                    return (<li key={"cate-" + category.id} className={"border-b-[1px] border-gray-200 w-[100%] py-2"}>
                        <div className={"flex flex-row items-center group cursor-pointer justify-between"}
                            onClick={() => {console.log("clicked" + category.name); toggleCategory(category.id)}}
                            >
                            <span className={"material-icons text-md group-hover:text-blue-500 " + (findIndexCate !== -1 ? "rotate-90" : "")}>arrow_forward_ios</span>
                            <span className="font-medium text-md ms-4 whitespace-nowrap truncate w-[100%] me-2">{category.name}</span>
                            <span className="material-icons text-xl hover:text-blue-500 cursor-pointer me-2"
                                onClick={(e) => {e.stopPropagation()}}
                                >edit</span>
                            <span className="material-icons text-xl hover:text-red-500 cursor-pointer"
                                onClick={(e) => {e.stopPropagation()}}
                                >delete</span>
                        </div>
                        <div className={"ms-6 " + (findIndexCate !== -1 ? "" : "hidden")}>
                            <p>{category.name}</p>
                            {category.questions && <ul className="pe-4">
                                {
                                    category.questions.map(question => {
                                        const openCate = findIndexCate !== -1 ? openCategories[findIndexCate] : null;
                                        const findIndexQuestion = openCate && openCate.openQuestions ? openCate.openQuestions.findIndex(item => item === question.id) : -1; 
                                        return <li key={"ques-" + question.id} className={"border-b-[1px] border-gray-200 w-[100%] py-1"}>
                                            <div className={"flex flex-row items-end group cursor-pointer justify-between"}
                                                onClick={() => {console.log("toggle question"); toggleQuestion(category.id, question.id)}}
                                                >
                                                <span className={"material-icons text-xl group-hover:text-blue-500 " + (findIndexQuestion !== -1 ? "rotate-90" : "")}>arrow_forward_ios</span>
                                                <span className="ms-4 whitespace-nowrap truncate w-[100%] me-2 leading-tight">
                                                    <span className="text-xs font-normal text-base/2">{question.level}</span><br></br>
                                                    <span className="text-md font-medium">{question.content}</span>
                                                </span>
                                                <span className="material-icons text-xl hover:text-blue-500 cursor-pointer me-2"
                                                    onClick={(e) => {e.stopPropagation(); window.location.href = "/questions/edit?id=" + question.id}}
                                                    >edit</span>
                                                <span className="material-icons text-xl hover:text-red-500 cursor-pointer"
                                                    onClick={(e) => {e.stopPropagation()}}
                                                    >delete</span>
                                            </div>
                                            <div className={"ms-6 " + (findIndexQuestion !== -1 ? "" : "hidden")}>
                                                <p className="text-md">{question.content}</p>
                                                <ul>
                                                    {question.answers.map((answer, index) => {
                                                        return (<li key={"answ-" + question.id + "-" + index}>
                                                            <span className={answer.is_correct ? "font-bold" : ""}>{getFirstAnswer(index)}. {answer.content}</span>
                                                        </li>);
                                                    })}
                                                </ul>
                                            </div>
                                        </li>
                                    })
                                }
                            </ul>}
                        </div>
                    </li>);
                })}
            </ul>
        </div>
    </>);
}
export default Course;