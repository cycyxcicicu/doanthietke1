import React, {  useEffect, useImperativeHandle, useState } from "react";
import * as XLSX from "xlsx";
const Taocauhoi = ({ questionsListRef , onQuestionsChange }) => {
  const [question, setQuestion] = useState("");
  const [options, setOptions] = useState("");
  const [correctAnswers, setCorrectAnswers] = useState("");
  const [questionsList, setQuestionsList] = useState([]);
  const [editingIndex, setEditingIndex] = useState(null);
  const [errors, setErrors] = useState({});
  const [hoveredIndex, setHoveredIndex] = useState(null);

  useEffect(() => {
	console.log("Danh sách câu hỏi: ", questionsListRef);
	const transformedQuestions = transformQuestions(questionsListRef[0]);
	setQuestionsList (transformedQuestions);
    console.log("Danh sách câu hỏi: ", transformedQuestions);
  }, []);

  const transformQuestions = (questions) => {
	return questions.map((item) => ({
		questionsid:item.questionsid || "",
	  question: item.content || "", // Nếu thiếu, để giá trị mặc định là rỗng
	  options: item.choive
		? item.choive.split("|").map((opt) => opt.trim())
		: [], // Nếu thiếu choive, trả về mảng rỗng
	  correctAnswers: item.answer ? [item.answer] : [], // Nếu thiếu answer, trả về mảng rỗng
	}));
  };
  
  
  const handleAddOrEditQuestion = () => {
    const newErrors = {};

    // Kiểm tra các trường trống
    if (!question.trim()) {
      newErrors.question = "Tên câu hỏi không được để trống.";
    }
    if (!options.trim()) {
      newErrors.options = "Các đáp án không được để trống.";
    }
    if (!correctAnswers.trim()) {
      newErrors.correctAnswers = "Các đáp án đúng không được để trống.";
    }

    const optionsArray = options
      .split("|")
      .map((opt) => opt.trim())
      .filter((opt) => opt !== "");
    const correctAnswersArray = correctAnswers
      .split("|")
      .map((ans) => ans.trim())
      .filter((ans) => ans !== "");

    // Kiểm tra đáp án trùng lặp
    const duplicates = optionsArray.filter(
      (item, index) => optionsArray.indexOf(item) !== index
    );
    if (duplicates.length > 0) {
      newErrors.options = `Đáp án bị trùng: ${duplicates.join(",")}`;
    }

    // Kiểm tra đáp án đúng không tồn tại
    const invalidCorrectAnswers = correctAnswersArray.filter(
      (ans) => !optionsArray.includes(ans)
    );
    if (invalidCorrectAnswers.length > 0) {
      newErrors.correctAnswers = `Đáp án đúng không tồn tại: ${invalidCorrectAnswers.join(", ")}`;
    }

    // Nếu có lỗi, cập nhật trạng thái và dừng thực thi
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    // Thêm hoặc sửa câu hỏi
    if (editingIndex !== null) {
      const updatedQuestions = [...questionsList];
      updatedQuestions[editingIndex] = {
        question,
        options: optionsArray,
        correctAnswers: correctAnswersArray,
      };
      setQuestionsList(updatedQuestions);
	  onQuestionsChange(updatedQuestions);

    } else {
      const newQuestion = {
        question,
        options: optionsArray,
        correctAnswers: correctAnswersArray,
      };
      setQuestionsList([...questionsList, newQuestion]);
	  onQuestionsChange([...questionsList, newQuestion]);
    }

    // Reset form và lỗi
    setQuestion("");
    setOptions("");
    setCorrectAnswers("");
    setEditingIndex(null);
    setErrors({});
  };

  const handleEditQuestion = (index) => {
    const questionToEdit = questionsList[index];
    setQuestion(questionToEdit.question);
    setOptions(questionToEdit.options.join("|"));
    setCorrectAnswers(questionToEdit.correctAnswers.join("|"));
    setEditingIndex(index);
  };

  const handleDeleteQuestion = (index) => {
    const updatedQuestions = questionsList.filter((_, i) => i !== index);
    setQuestionsList(updatedQuestions);
	onQuestionsChange(updatedQuestions);
    setQuestion("");
    setOptions("");
    setCorrectAnswers("");
    setEditingIndex(null);
    setErrors({});
  };
  const handleFileInput = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        try {
          const binaryStr = e.target.result;
          const workbook = XLSX.read(binaryStr, { type: "binary" });
          const sheetName = workbook.SheetNames[0]; // Lấy tên sheet đầu tiên
          const sheet = workbook.Sheets[sheetName];
           // Chuyển sheet thành mảng các mảng
        const jsonData = XLSX.utils.sheet_to_json(sheet, { header: 1 });

        // Bỏ qua tiêu đề cột (hàng đầu tiên) nếu có
        const rows = jsonData.slice(1);

        // Chuyển đổi thành định dạng mong muốn
        const formattedData = rows.map((row) => ({
          content: row[0],          // Cột 1: Nội dung câu hỏi
          answer: row[1],           // Cột 2: Đáp án đúng
          choive: row.slice(2).join("|"), // Cột 3 trở đi: Các lựa chọn, nối bằng ký tự "|"
        }));
		if (formattedData.length >0)
        {const updatedQuestionsList = [...questionsList, ...transformQuestions(formattedData)];
			setQuestionsList(updatedQuestionsList);
			onQuestionsChange(updatedQuestionsList);
			console.log(updatedQuestionsList)

		}
        } catch (error) {
          console.error("Lỗi đọc file Excel:", error);
          alert("File không hợp lệ, vui lòng chọn file Excel đúng định dạng.");
        }
      };
      reader.readAsBinaryString(file);
    }
    // Reset giá trị của input để đảm bảo xử lý lại khi chọn file giống nhau
    e.target.value = null;
  };
  
  useImperativeHandle(questionsListRef, () => questionsList);
  return (
    <div className="flex h-[80vh] bg-gray-50 p-2">
      {/* Danh sách câu hỏi */}
      <div className="flex-1 overflow-y-auto max-h-full p-6 border-r border-gray-200 bg-white">
        <h2 className="text-lg font-bold mb-4">Danh sách câu hỏi</h2>
        {questionsList.length > 0 ? (
          questionsList.map((q, index) => (
			
            <div
              key={index}
              className="relative mb-6 border-b pb-4"
			  id={q.questionsid ? q.questionsid : undefined}
              onMouseEnter={() => setHoveredIndex(index)}
              onMouseLeave={() => setHoveredIndex(null)}
            >
              <h3
                className="text-lg font-bold cursor-pointer"
                onClick={() => handleEditQuestion(index)}
              >
                Câu {index + 1}: {q.question}
              </h3>
              <ul className="mt-2 space-y-1" >
                {q.options.map((option, idx) => (
                  <li key={idx} >
                    <input
                      type="checkbox"
                      checked={q.correctAnswers.includes(option)}
                      readOnly
                      className="mr-2"
                    />
                    {option}
                  </li>
                ))}
              </ul>

              {/* Nút xóa hiển thị khi hover */}
              {hoveredIndex === index && (
                <button
                  onClick={() => handleDeleteQuestion(index)}
                  className="absolute top-2 right-2 bg-red-500 text-white rounded-full px-3 py-1 text-sm shadow-md hover:bg-red-600"
                >
                  Xóa
                </button>
              )}
            </div>
          ))
        ) : (
          <p className="text-gray-500 text-center">Chưa có câu hỏi nào được thêm.</p>
        )}
      </div>

      {/* Form thêm/sửa câu hỏi */}
      <div className="w-1/3 bg-white p-4 border-l border-gray-200 shadow-md ml-4 flex flex-col h-full">
        <div className="flex-grow">
          <h3 className="text-lg font-bold mb-4">
            {editingIndex !== null ? "Chỉnh sửa câu hỏi" : "Thêm câu hỏi mới"}
          </h3>

          <div className="space-y-4">
            <div>
              <label className="block font-medium mb-1">Tên câu hỏi</label>
              <input
                type="text"
                placeholder="Nhập nội dung câu hỏi"
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
                className={`w-full border rounded-lg p-2 ${
                  errors.question ? "border-red-500" : ""
                }`}
              />
              {errors.question && (
                <p className="text-red-500 text-sm mt-1">{errors.question}</p>
              )}
            </div>
            <div>
              <label className="block font-medium mb-1">
                Các đáp án (cách nhau bằng ký tự `|`)
              </label>
              <input
                type="text"
                placeholder="Nhập các đáp án "
                value={options}
                onChange={(e) => setOptions(e.target.value)}
                className={`w-full border rounded-lg p-2 ${
                  errors.options ? "border-red-500" : ""
                }`}
              />
              {errors.options && (
                <p className="text-red-500 text-sm mt-1">{errors.options}</p>
              )}
            </div>

            <div>
              <label className="block font-medium mb-1">
                Các đáp án đúng (cách nhau bằng ký tự `|`)
              </label>
              <input
                type="text"
                placeholder="Nhập đáp án đúng "
                value={correctAnswers}
                onChange={(e) => setCorrectAnswers(e.target.value)}
                className={`w-full border rounded-lg p-2 ${
                  errors.correctAnswers ? "border-red-500" : ""
                }`}
              />
              {errors.correctAnswers && (
                <p className="text-red-500 text-sm mt-1">
                  {errors.correctAnswers}
                </p>
              )}
            </div>
          </div>
		    {/* Input file */}
			<div className="mb-4">
        <label className="block text-gray-700 font-bold mb-2">Chọn file Excel:</label>
        <input
          type="file"
          accept=".xlsx, .xls, .csv"
          onChange={handleFileInput}
          className="border rounded p-2 w-full"
        />
      </div>
        </div>
        <button
          onClick={handleAddOrEditQuestion}
          className="w-full bg-purple-500 text-white py-2 rounded-lg mt-4 hover:bg-purple-600 transition"
        >
          {editingIndex !== null ? "Sửa câu hỏi" : "Tạo câu hỏi"}
        </button>
      </div>
    </div>
  );
};

export default Taocauhoi;
