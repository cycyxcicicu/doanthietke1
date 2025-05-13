import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getExam } from "../../services/ExamService";
const TronDe = () => {
    const { id } = useParams();
    const [exam, setExam] = useState({});
    useEffect(() => {
        getExam(id)
            .then((res) => {
                setExam(res)
            })
    }, [])
    const renderRunPart = (runPart) => {
        if (runPart.type === 'TEXT')
            return <>
                <span className={`${runPart.to_dam ? 'font-bold' : ''} ${runPart.gach_chan ? 'underline' : ''} ${runPart.in_nghieng ? 'italic' : ''} text-[#${runPart.ma_mau_chu}]`}>
                    {runPart.text}
                </span>
                {runPart.isEndLine && <br></br>}
            </>
        else if (runPart.type === 'IMAGE')
            return <>
                {runPart.images && runPart.images.map(item => {
                    return <img className="h-[200px]" src={`${item.link}`} />
                })}
            </>
    }
    const renderDescriptionQuestion = (description) => {
        return <>
            {description.contents && description.contents.map(item => {
                return <>
                    {renderRunPart(item)}
                </>
            })}
        </>
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
                <strong>&lt;g2&gt;</strong> (Nhóm trộn cả câu hỏi và đáp án)
            </>
        }
    }
    const getNumberAnswer = (index) => {
        return String.fromCharCode(65 + index);
    }
    const renderQuestion = (question) => {
        return <>
            <strong>Câu hỏi:</strong> {question.contents && question.contents.map(item => {
                return renderRunPart(item)
            })} <br></br>
            <strong>Trả lời:</strong> <br></br>
            {question.answers && question.answers.map((answer, index) => {
                return <><span className={`font-bold ${answer.answer ? 'text-red-600' : ''}`}>{answer.isNotMix ? '#' : ''}{getNumberAnswer(index)}. </span>
                    {answer.contents.map((content) => {
                        return <>{renderRunPart(content)}</>
                    })} 
                    <br></br>
                </>
            })}
        </>
    }
    return (
        <div className="container p-4 pt-2 pb-12">
            <h1 className="text-2xl font-bold py-4 pb-6">Xem lại đề của bạn</h1>
            <div className="flex flex-row mb-2 items-stretch">
                <div className="bg-purple-400">
                    <label for={"ten_de_thi"} className="text-sm text-white text-center ms-2">Tên đề thi</label>
                    <input
                        name="ten_de_thi"
                        type="text"
                        className="min-w-[20ch] border border-dashed outline-none rounded ms-2 max-w-[60ch] text-sm p-1 border-2 border-purple-300"
                        onInput={(e) => {
                            e.target.style.width = `${(e.target.value.length || 1) + 1}ch`;
                        }}
                        required
                        />
                    <label for={"so_de_muon_tao"} className="text-sm ms-2 text-white">Số lượng đề</label>
                    <input
                        name="so_luong_de"
                        type="number"
                        className="border border-dashed outline-none rounded ms-2 text-sm p-1 border-2 border-purple-300 text-center"
                        min={1}
                        max={1000}
                        required
                        />
                </div>
                <button type="button" className="bg-purple-400 hover:bg-purple-500 p-1 rounded ms-4 text-white text-sm px-2">Trộn & xuất file</button>
            </div>
            <ul className="border-[2px] border-dashed border-purple-300 bg-purple-200 p-4 rounded">
                {exam.groups && exam.groups.map(group => {
                    return <>
                        <span>- {renderNoteGroup(group.name)}</span>
                        <ul>
                            {group.items && group.items.map(item => {
                                return <li key={item.id}>
                                    {item.contents && item.contents.map(content => renderDescriptionQuestion(content))}
                                </li>
                            })}
                            {group.questions && group.questions.map(question => {
                                return <li key={question.id}>
                                    {renderQuestion(question)}
                                </li>
                            })}
                        </ul>
                    </>
                })}
            </ul>
        </div>
    );
}

export default TronDe;