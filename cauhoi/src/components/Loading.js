export const Loading = () => {
    return <div className={`absolute h-[100%] w-[100%] flex items-center justify-center z-[1000] bg-black/20 inset-0`}>
        <div className="flex gap-2">
            <div className="w-5 h-5 rounded-full animate-pulse bg-purple-800"></div>
            <div className="w-5 h-5 rounded-full animate-pulse bg-purple-800"></div>
            <div className="w-5 h-5 rounded-full animate-pulse bg-purple-800"></div>
        </div>
    </div>
}