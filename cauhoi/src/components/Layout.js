import React, {useState} from "react";
import Navbar from "./Navbar"; // Hoặc Sidebar, Footer, v.v.
import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";

const Layout = () => {
    const [isSidebarExpanded, setIsSidebarExpanded] = useState(false); // Trạng thái Sidebar
    return (
        <>
            <div className="flex h-screen">
                {/* Sidebar */}
                <Sidebar
                    isExpanded={isSidebarExpanded}
                    toggleSidebar={setIsSidebarExpanded}
                />

                {/* Nội dung chính */}
                <div
                    className={`flex-1 transition-all duration-300 ${isSidebarExpanded ? "ml-64" : "ml-16"}`}
                >
                    <div>
                        <Navbar />
                        <Outlet />
                    </div>
                </div>
            </div>
        </>
    );
};

export default Layout;