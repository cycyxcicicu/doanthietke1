import { useEffect, useState } from "react"

const SideBar = ({ exams, current_exam, set_current_exam }) => {
    const [isOpen, setIsOpen] = useState(false);
    useEffect(() => {
        if (!current_exam) setIsOpen(true);
        else setIsOpen(false);
    }, [current_exam])
    return <div className="select-none fixed right-0 top-0 bottom-0 w-[100%] sm:w-auto">
        <div className="flex flex-row justify-end pt-[80px] pe-[20px] w-[100%] sm:w-auto ps-[20px]">
            {isOpen &&
                <div className="rounded-lg duration-300 ease-in max-w-full w-[300px] h-[500px] bg-purple-500 me-2">
                    <ul className="p-2 max-h-full overflow-y-auto">
                        {exams && [...exams].reverse().map((exam, index) => {
                            return <>
                                {index <= 1 ? <li className="text-xs text-white text-right my-1">{index === 0 ? 'Mới nhất' : 'Trước đó'}</li> : ''}
                                <li className={`cursor-pointer p-2 border-b border-white text-nowrap rounded overflow-hidden truncate hover:opacity-80 hover:bg-white hover:!text-purple-500 ${current_exam === exam.id ? 'bg-white text-purple-500' : 'text-white'}`} key={exam.id}
                                    onClick={() => {set_current_exam(exam.id); setIsOpen(false)}}
                                >
                                    {exam.name}
                                </li>
                            </>
                        })}
                        {!exams && <p className="text-white text-center mt-4">
                            Chưa có bản ghi nào
                        </p>}
                    </ul>
                </div>
            }
            <div className="flex flex-col">
                <span className="duration-300 ease-in w-[50px] h-[50px] material-icons text-white bg-purple-500 rounded-full text-3xl/[50px] text-center cursor-pointer hover:bg-purple-600"
                    onClick={() => {setIsOpen(true)}}
                >
                    segment
                </span>
                {isOpen &&
                    <span className="duration-300 ease-in mt-2 w-[50px] h-[50px] border-gray-400 border material-icons text-gray-300 bg-white rounded-full text-3xl/[48px] text-center cursor-pointer hover:text-red-500"
                        onClick={() => {setIsOpen(false)}}
                    >
                        close
                    </span>
                }
            </div>
        </div>
    </div>
}

export default SideBar;