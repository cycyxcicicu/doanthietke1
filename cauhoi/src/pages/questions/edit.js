import React, {  useEffect, useImperativeHandle, useState } from "react";
import * as XLSX from "xlsx";
const SuaCauHoi = () => {
  document.title = "Sửa câu hỏi";
  const [courses, setCourses] = useState([
    {id: 101, stt: 1, name: 'Toán 12', time_create: '2025-04-24 11:11:11', count_categories: 4, count_questions: 1},
    {id: 102, stt: 2, name: 'Tiếng Anh 12', time_create: '2025-04-23 11:11:11', count_categories: 4, count_questions: 0},
    {id: 103, stt: 3, name: 'Vật Lý 12', time_create: '2025-04-22 11:11:11', count_categories: 4, count_questions: 0},
    {id: 104, stt: 4, name: 'Hóa Học 12', time_create: '2025-04-21 11:11:11', count_categories: 4, count_questions: 0}
  ]);

  const [categories, setCategories] = useState([
    {id: 201, stt: 1, name: "Chương 1", time_create: '2024-11-10 11:11:11', course_id: 101},
    {id: 202, stt: 1, name: "Chương 2", time_create: '2024-11-10 11:11:11', course_id: 101},
    {id: 203, stt: 1, name: "Chương 3", time_create: '2024-11-10 11:11:11', course_id: 101},
    {id: 204, stt: 1, name: "Chương 4", time_create: '2024-11-10 11:11:11', course_id: 101}
  ])

  const [question, setQuestion] = useState({
    content: "",
    level: "easy",
    answers: [
      { content: "", is_correct: false },
      { content: "", is_correct: false },
    ],
    course_id: "",
    category_id: ""
  });

  const handleAnswerChange = (index, field, value) => {
    const updated = [...question.answers];
    updated[index][field] = field === "is_correct" ? value : value;
    setQuestion({ ...question, answers: updated });
  };

  const addAnswer = () => {
    setQuestion({
      ...question,
      answers: [...question.answers, { content: "", is_correct: false }],
    });
  };

  const removeAnswer = (index) => {
    const updated = question.answers.filter((_, i) => i !== index);
    setQuestion({ ...question, answers: updated });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: Gửi `question` đến API server
    console.log("Submitting question:", question);
  };
  return (<>
    <div className="my-6 py-6 container px-2">
        <button className='text-purple-500 font-medium flex flex-row hover:opacity-75'
          onClick={() => {window.location.href = "/questions"}}
					>
          <span className='material-icons me-1'>keyboard_backspace</span>
          Trở về
      </button>
      <h1 className="text-2xl font-medium mt-2">Sửa câu hỏi</h1>
      <form onSubmit={handleSubmit} className="space-y-4 mt-6">
        <div>
          <label className="block text-sm font-medium mb-1">Nội dung câu hỏi</label>
          <textarea
            value={question.content}
            onChange={(e) => setQuestion({ ...question, content: e.target.value })}
            className="w-full border rounded p-2 focus:outline-none focus:border-blue-200"
            rows={3}
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Ảnh</label>
          <input type="file" className="hidden" id="upload-files" />
          <ul className="flex flex-row">
            <label htmlFor="upload-files" className="w-[100px] h-[100px] bg-gray-200 flex items-center justify-center cursor-pointer">
              <span className='material-icons text-3xl'>add</span>
            </label>
          </ul>
        </div>

        <div>
          <label className="block text-sm font-medium mb-2">Đáp án</label>
          {question.answers.map((ans, index) => (
            <div key={index} className="flex items-center mb-2 space-x-2">
              <input
                type="text"
                className="w-full border rounded p-2 focus:outline-none focus:border-blue-200"
                placeholder={`Đáp án ${index + 1}`}
                value={ans.content}
                onChange={(e) => handleAnswerChange(index, "content", e.target.value)}
                required
              />
              <label className="flex items-center space-x-1 text-sm">
                <input
                  type="checkbox" 
                  checked={ans.is_correct}
                  onChange={(e) => handleAnswerChange(index, "is_correct", e.target.checked)}
                />
                <span>Đúng</span>
              </label>
              {question.answers.length > 2 && (
                <button
                  type="button"
                  onClick={() => removeAnswer(index)}
                  className="text-red-500 hover:underline text-sm focus:outline-none focus:border-blue-200"
                >
                  Xóa
                </button>
              )}
            </div>
          ))}
          <button
            type="button"
            onClick={addAnswer}
            className="mt-2 text-blue-600 hover:underline text-sm"
          >
            + Thêm đáp án
          </button>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Mức độ</label>
          <select
            value={question.level}
            onChange={(e) => setQuestion({ ...question, level: e.target.value })}
            className="w-full border rounded p-2 focus:outline-none focus:border-blue-200"
          >
            <option value="easy">Dễ</option>
            <option value="medium">Trung bình</option>
            <option value="hard">Khó</option>
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Khóa học</label>
          <select
            value={question.course_id}
            onChange={(e) => setQuestion({ ...question, course_id: e.target.value })}
            className="w-full border rounded p-2 focus:outline-none focus:border-blue-200"
          >
            <option value={""}>Chọn khóa học</option>
            {courses && courses.map(item => <option value={item.id}>{item.id} - {item.name}</option>)}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Chủ đề</label>
          <select
            value={question.category_id}
            onChange={(e) => setQuestion({ ...question, category_id: e.target.value })}
            className="w-full border rounded p-2 focus:outline-none focus:border-blue-200"
          >
            <option value={""}>Chọn chủ đề</option>
            {question.course_id && categories.map(item => <option value={item.id}>{item.id} - {item.name}</option>)}
          </select>
        </div>

        <div className="text-right">
          <button
            type="button"
            className="bg-purple-500 text-white px-4 py-2 rounded hover:bg-purple-600"
          >
            Lưu
          </button>
        </div>

      </form>
    </div>
  </>);
};

export default SuaCauHoi;
