import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { getExam } from "../../services/api/ExamService";
import { exportFile } from "../../services/api/FileService";
import SideBar from "../../components/Sidebar";
const TronDe = () => {
    document.title = 'Trộn đề';
    const [searchParams] = useSearchParams();
    const [id, setId] = useState();
    const [exam, setExam] = useState({});
    const [name, setName] = useState('');
    const [count, setCount] = useState(1);
    const [exams, setExams] = useState([]);
    useEffect(() => {
        const json = localStorage.getItem('uploads');
        if (json)
            setExams(JSON.parse(json))
        setId(searchParams.get("id"))
    }, [])
    useEffect(() => {
        if (!id) return;
    
        getExam(id)
            .then((res) => {
                setExam(res)
                setName(res.name)
                setCount(1)
            })
    }, [id])
    const exportSubmit = () => {
        exportFile(exam.id, name, count);
    }
    const renderNoteGroup = (name) => {
        switch (name) {
            case "NONE": return <>
                <strong>&lt;none&gt;</strong> (Nhóm trộn không có điều kiện)
            </>
            case "g0": return <>
                <strong>&lt;g0&gt;</strong> (Nhóm không trộn)
            </>
            case "g1": return <>
                <strong>&lt;g1&gt;</strong> (Nhóm chỉ trộn các câu hỏi)
            </>
            case "g2": return <>
                <strong>&lt;g2&gt;</strong> (Nhóm chỉ trộn các đáp án)
            </>
            case "g3": return <>
                <strong>&lt;g3&gt;</strong> (Nhóm trộn cả câu hỏi và đáp án)
            </>
        }
    }
    const getNumberAnswer = (index) => {
        return String.fromCharCode(65 + index);
    }
    const renderRunPart = (runPart) => {
        if (runPart.type === 'TEXT') {
            return <>
                <span key={runPart.id} className={`${runPart.to_dam ? 'font-bold' : ''} ${runPart.gach_chan ? 'underline' : ''} ${runPart.in_nghieng ? 'italic' : ''}`}>
                    {runPart.text}
                </span>
                {runPart.isEndLine && <br></br>}
            </>
        }
        else if (runPart.type === 'IMAGE')
            return <span key={runPart.id}>
                {runPart.images && runPart.images.map((item, index) => (
                    <img
                        key={`${runPart.id}-${index}`}
                        src={`http://localhost:8080/cauhoi${item.link}`}
                        style={{
                        width: runPart.width ? `${runPart.width}px` : 'auto',
                        height: runPart.height ? `${runPart.height}px` : '200px'
                        }}
                        className="rounded shadow"
                    />
                    ))
                }
            </span>
        else if (runPart.end_line)
            return <br></br>
        }
    const renderDescriptionQuestion = (description) => {
        return <>
            {description.contents && description.contents.map((item, index) => (
                <span key={item.id || index}>
                    {renderRunPart(item)}
                </span>
            ))}
        </>
    }
    const renderQuestion = (question) => {
        return <>
            <strong>Câu hỏi:</strong> {question.contents && question.contents.map((item, index) => (
                <span key={item.id || index}>
                    {renderRunPart(item)}
                </span>
            ))}
            <br></br>
            <strong>Trả lời:</strong> <br></br>
            {question.answers && question.answers.map((answer, index) => {
                return <><span key={index} className={`font-bold ${answer.answer ? 'text-red-600' : ''}`}>{answer.isNotMix ? '#' : ''}{getNumberAnswer(index)}. </span>
                    {answer.contents.map((content, i) => (
                        <span key={content.id || i}>
                            {renderRunPart(content)}
                        </span>
                    ))}
                    <br></br>
                </>
            })}
        </>
    }
    return (
        <div className="container p-4 pt-2 pb-12 min-h-[80vh]">
            <SideBar exams={exams} current_exam={id} set_current_exam={setId} />
            <button className='text-purple-500 font-medium flex flex-row hover:opacity-75 top-[10px] left-[10px] focus:outline-none'
                onClick={() => {window.location.href = "/"}}
            >
                <span className='material-icons me-1'>keyboard_backspace</span>
                Trở về
            </button>
            {Object.keys(exam).length > 0 &&
            <>
                <h1 className="text-2xl font-bold py-4 pb-6">Xem lại đề</h1>
                <div className="flex flex-row mb-2 items-stretch">
                    <div className="bg-purple-400">
                        <label htmlFor={"ten_de_thi"} className="text-sm text-white text-center ms-2">Tên đề thi</label>
                        <input
                            name="ten_de_thi"
                            type="text"
                            className="min-w-[20ch] border border-dashed outline-none rounded ms-2 max-w-[60ch] text-sm p-1 border-2 border-purple-300"
                            onInput={(e) => {
                                e.target.style.width = `${(e.target.value.length || 1) + 1}ch`;
                            }}
                            onChange={(e) => {
                                setName(e.target.value)
                            }}
                            value={name}
                            required
                            />
                        <label htmlFor={"so_de_muon_tao"} className="text-sm ms-2 text-white">Số lượng đề</label>
                        <input
                            name="so_luong_de"
                            type="number"
                            className="border border-dashed outline-none rounded ms-2 text-sm p-1 border-2 border-purple-300 text-center"
                            min={1}
                            max={20}
                            onChange={(e) => {
                                setCount(e.target.value)
                            }}
                            value={count}
                            required
                            />
                    </div>
                    <button type="button" onClick={exportSubmit} className="bg-purple-400 hover:bg-purple-500 p-1 rounded ms-4 text-white text-sm px-2">Trộn & xuất file</button>
                </div>
                <ul className="border-[2px] border-dashed border-purple-300 bg-purple-200 p-4 rounded">
                    {exam && exam.groups && exam.groups.map(group => {
                        return <div key={group.id}>
                            {group.not_mix ? (<><strong>Nhóm giữ nguyên vị trí</strong><br></br></>) : ""}
                            <span>{renderNoteGroup(group.name)}</span>
                            <ul>
                                {group.descriptions && group.descriptions.map(description => {
                                    return <li key={description.id}>
                                        {renderDescriptionQuestion(description)}
                                    </li>
                                })}
                                {group.questions && group.questions.map(question => {
                                    return <li key={question.id}>
                                        {renderQuestion(question)}
                                    </li>
                                })}
                            </ul>
                        </div>
                    })}
                </ul>
            </>
            }
        </div>
    );
}

export default TronDe;