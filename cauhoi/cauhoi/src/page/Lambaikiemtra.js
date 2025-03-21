import React, { useState, useEffect, useRef } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { laycauhoi ,createlichsu,laylichsu} from '../apiService';
import { userId } from '../utils';
import { toast, ToastContainer } from "react-toastify";
import { isTokenValid } from '../utils';
const Lambaikiemtra = () => {

  const questions = [
    { id: 1, question: "Thủ đô của Việt Nam là gì?", options: ["Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng", "Huế", "Cần Thơ"] },
    { id: 2, question: "1 + 1 bằng mấy?", options: ["1", "2", "3", "4", "5"] },
    { id: 3, question: "Màu của lá cây là gì?", options: ["Xanh", "Đỏ", "Vàng", "Tím", "Trắng"] },
    { id: 4, question: "Mùa hè nóng hay lạnh?", options: ["Nóng", "Lạnh", "Mát", "Khô", "Ẩm"] },
    { id: 5, question: "Số lớn nhất trong các số sau: 10, 20, 30, 40, 50?", options: ["10", "20", "30", "40", "50"] },
  ];
  const questionRefs = useRef([]);
  const location = useLocation(); // Lấy thông tin URL hiện tại

const queryParams = new URLSearchParams(location.search); // Tạo URLSearchParams từ query string

const testid = queryParams.get('testid');
const timeParam = queryParams.get('time');

// Chuyển giá trị 'time' từ chuỗi sang số (kiểu int)
const duration = timeParam ? parseInt(timeParam, 10) : 0;  // Nếu không có tham số 'time', gán mặc định là 0

  const [isStarted, setIsStarted] = useState(false);
  const [timeLeft, setTimeLeft] = useState(duration * 60);
  const [answers, setAnswers] = useState({});
  const [history, setHistory] = useState([]);
  const [selectedHistory, setSelectedHistory] = useState(null);
  const [currentQuestion, setCurrentQuestion] = useState([]);
  
  

  useEffect(() => {
	
	getcauhoi()
	getlichsu()
    let timer;
    if (isStarted && timeLeft > 0) {
      timer = setInterval(() => {
        setTimeLeft((prev) => prev - 1);
      }, 1000);
    } else if (isStarted && timeLeft === 0) {
      handleSubmit("Hết giờ! Bài kiểm tra đã được tự động nộp.");
    }
    return () => clearInterval(timer);
  }, [isStarted, timeLeft]);

  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${minutes}:${secs < 10 ? "0" : ""}${secs}`;
  };
  const getlichsu = async () => {
	try {
		
		const { result, code } = await laylichsu(testid,userId());
		if (code === 1000) {

			// Phân loại lịch sử làm bài theo `number`
			const groupedHistory = result.reduce((acc, item) => {
				const attemptNumber = item.number; // Lấy số lần làm bài
				if (!acc[attemptNumber]) {
				  acc[attemptNumber] =  { entries: [], totalScore: 0 };
				}
				acc[attemptNumber].entries.push(item);
    			acc[attemptNumber].totalScore = parseFloat(item.score);
				return acc;
			  }, {});
			  setHistory(groupedHistory); // Lưu lịch sử vào state
		} else {
			// Xử lý khi không có dữ liệu hợp lệ từ API
			console.error("Không có dữ liệu bài kiểm tra.");
		}
	} catch (error) {
		console.error("Lỗi khi gọi API:", error);
	}
}
const getcauhoi = async () => {
		try {
			console.log(testid)
			
			const { result, code } = await laycauhoi(testid);
			if (code === 1000) {
				// Cập nhật dữ liệu lớp học từ API vào state
				console.log(transformQuestions(result))

				setCurrentQuestion(transformQuestions(result));  // Cập nhật `lophoc` với dữ liệu từ API
			} else {
				// Xử lý khi không có dữ liệu hợp lệ từ API
				console.error("Không có dữ liệu bài kiểm tra.");
			}
		} catch (error) {
			console.error("Lỗi khi gọi API:", error);
		}
	}
	const transformQuestions = (questions) => {
		return questions.map((item) => ({
			questionsid:item.questionsid || "",
		  question: item.content || "", // Nếu thiếu, để giá trị mặc định là rỗng
		  options: item.choive
			? item.choive.split("|").map((opt) => opt.trim())
			: [], // Nếu thiếu choive, trả về mảng rỗng
		 correctAnswers: item.answer ? [item.answer] : [],
		}));
	  };
  const handleStart = () => {
    setIsStarted(true);
	setSelectedHistory(null);
	
  };

  const handleSubmit = async () => {
	const totalQuestions = currentQuestion.length;
  
	// Kiểm tra xem tất cả các câu hỏi đã được trả lời chưa
	const unansweredQuestions = currentQuestion.filter(
	  (q) => !answers[q.questionsid]
	);
  
	// Nếu còn thời gian và có câu chưa trả lời, thông báo và không nộp bài
	if (unansweredQuestions.length > 0 && timeLeft > 0) {
	  toast.warning("Vui lòng trả lời hết các câu hỏi trước khi nộp bài.", {
		position: 'top-right',
		autoClose: 5000,
		hideProgressBar: false,
		closeOnClick: true,
		pauseOnHover: true,
		draggable: true,
	  });
	  return;  // Không nộp bài nếu còn câu chưa trả lời
	}
  
	// Nếu hết giờ, tự động nộp bài
	const pointsPerQuestion = totalQuestions > 0 ? 10 / totalQuestions : 0;
  
	const submittedAnswers = currentQuestion.map((q) => ({
	  questionid: q.questionsid,
	  answers: answers[q.questionsid] || "Chưa trả lời", // Câu trả lời được chọn
	  isCorrect: answers[q.questionsid] === q.correctAnswers[0], // So sánh đúng/sai
	  userid: userId(),
	  testid: testid,
	}));
  
	// Tính tổng điểm
	const totalScore = submittedAnswers.reduce((score, item) => {
	  return item.isCorrect ? score + pointsPerQuestion : score;
	}, 0);
  
	const updatedSubmittedAnswers = submittedAnswers.map((item) => ({
	  ...item,
	  score: totalScore.toFixed(2), // Thêm tổng điểm vào từng phần tử (nếu cần dạng chuỗi)
	}));
  
	console.log("Kết quả nộp bài:", updatedSubmittedAnswers);
  
	try {
	  const { message, code } = await createlichsu(updatedSubmittedAnswers);
	  console.log(message);
	  if (code === 1000) {
		toast.success(message, {
		  position: 'top-right',
		  autoClose: 5000,
		  hideProgressBar: false,
		  closeOnClick: true,
		  pauseOnHover: true,
		  draggable: true,
		});
	  } else {
		toast.error(message, {
		  position: 'top-right',
		  autoClose: 5000,
		  hideProgressBar: false,
		  closeOnClick: true,
		  pauseOnHover: true,
		  draggable: true,
		});
	  }
	} catch (error) {
	  console.error('Nộp bài thất bại:', error);
	  toast.error('Có lỗi xảy ra, vui lòng thử lại!', {
		position: 'top-right',
		autoClose: 5000,
		hideProgressBar: false,
		closeOnClick: true,
		pauseOnHover: true,
		draggable: true,
	  });
	}
  
	console.log("Tổng điểm:", totalScore.toFixed(2)); // Hiển thị điểm với 2 chữ số thập phân
  
	resetTest();
  };
  

  const handleAnswerChange = (questionId, value) => {
    setAnswers((prev) => ({ ...prev, [questionId]: value }));
  };

  const scrollToQuestion = (index) => {
    if (questionRefs.current[index]) {
      questionRefs.current[index].scrollIntoView({ behavior: "smooth", block: "center" });
    }
  };

  const resetTest = () => {
    setIsStarted(false);
    setTimeLeft(duration * 60);
    setAnswers({});
  };

  const handleViewHistory = (attemptNumber) => {
    setSelectedHistory(attemptNumber);
  };

  return (
    <div className="grid grid-cols-12 gap-4 h-screen">
	
      {/* Cột trái: Đồng hồ và lịch sử */}
      <div className="col-span-2 bg-gray-100 p-4 rounded shadow flex flex-col justify-between h-screen sticky top-0">
	 
        <h3 className="text-xl font-bold text-center">Thời gian còn lại</h3>
        <div className="text-4xl font-bold text-red-600 text-center">
          {formatTime(timeLeft)}
        </div>
        {isStarted && (
          <button
            onClick={() => handleSubmit()}
            className="bg-green-500 text-white px-4 py-2 rounded mt-4 w-full"
          >
            Nộp bài
          </button>
        )}

        {!isStarted && (
          <div className="mt-4">
            <h4 className="text-lg font-bold mb-2">Lịch sử làm bài</h4>
            {Object.keys(history).length  > 0 ? (
              <div className="space-y-2">
                {Object.keys(history).map((attemptNumber) => (
                  <button
                    key={attemptNumber}
                    onClick={() => handleViewHistory(attemptNumber)}
                    className={`w-full py-2 rounded border ${
                      selectedHistory === attemptNumber
                        ? "bg-purple-500 text-white"
                        : "bg-gray-200"
                    }`}
                  >
                    Lần {attemptNumber}
                  </button>
                ))}
              </div>
            ) : (
              <p className="text-gray-500 text-center">Chưa có lịch sử.</p>
            )}
          </div>
        )}
      </div>

      {/* Cột giữa: Câu hỏi */}
      <div className="relative col-span-8 bg-white rounded shadow h-screen flex flex-col">
        <div className="sticky top-0 bg-white z-10 p-4 border-b">
          <h3 className="text-xl font-bold text-center">Danh sách câu hỏi</h3>
        </div>

        <div className="flex-grow overflow-y-auto p-4">
          {/* Hiển thị lịch sử nếu có */}
          {selectedHistory ? (
			<div>
  
  {selectedHistory && (
  <div>
  <h4 className="text-lg font-bold mb-4">
    Chi tiết Lần {selectedHistory} (Điểm: {history[selectedHistory].totalScore.toFixed(2)})
  </h4>
    {history[selectedHistory].entries.map((entry, index) => {
      const question = currentQuestion.find(
        (q) => q.questionsid === entry.questionid
      );
      const isCorrect = entry.answers === (question?.correctAnswers[0] || "");
      return (
        <div key={entry.historyid} className="mb-6">
          <p className="font-bold mb-2">
            Câu {index + 1}: {question ? question.question : "Câu hỏi không tồn tại"}
          </p>
          {question?.options.map((option, idx) => (
            <label key={idx} className="block flex items-center">
              <input
                type="radio"
                name={`history-question-${entry.historyid}`}
                value={option}
                disabled
                checked={entry.answers === option}
                className="mr-2"
              />
              {option}
              {option === entry.answers && isCorrect && (
                <span className="ml-2 text-green-500 font-bold">✔</span>
              )}
              {option === entry.answers && !isCorrect && (
                <span className="ml-2 text-red-500 font-bold">✖</span>
              )}
            </label>
          ))}
        </div>
      );
    })}
  </div>
)}

</div>

          ) : (
            /* Hiển thị danh sách câu hỏi nếu không chọn lịch sử */
            isStarted &&
            currentQuestion.map((q, index) => (
              <div
                key={q.questionsid}
                ref={(el) => (questionRefs.current[index] = el)}
                className="mb-6"
              >
                <p className="font-bold mb-2">
                  Câu {index + 1}: {q.question}
                </p>
                {q.options.map((option, idx) => (
                  <label key={idx} className="block">
                    <input
                      type="radio"
                      name={`question-${q.questionsid}`}
                      value={option}
                      onChange={() => handleAnswerChange(q.questionsid, option)}
                      className="mr-2"
                    />
                    {option}
                  </label>
                ))}
              </div>
            ))
          )}
        </div>

        {!isStarted && (
          <div className="border-t p-4 bg-white">
            <button
              onClick={handleStart}
              className="bg-purple-500 text-white py-3 rounded w-full text-lg"
            >
              Bắt đầu làm bài
            </button>
          </div>
        )}
      </div>

      {/* Cột phải: Liệt kê câu hỏi */}
      <div className="col-span-2 bg-gray-50 p-4 rounded shadow h-screen sticky top-0">
        <h3 className="text-xl font-bold mb-4 text-center">Danh sách câu hỏi</h3>
        <div className="grid grid-cols-5 gap-2">
          {currentQuestion.map((q, index) => (
            <button
              key={q.questionsid}
              onClick={() => scrollToQuestion(index)}
              className={`w-12 h-12 flex items-center justify-center rounded border ${
                answers[q.questionsid] ? "bg-green-500 text-white" : "bg-gray-200"
              }`}
            >
              {index + 1}
            </button>
          ))}
        </div>
      </div>
	  <ToastContainer/>
    </div>
  );
};

export default Lambaikiemtra;
