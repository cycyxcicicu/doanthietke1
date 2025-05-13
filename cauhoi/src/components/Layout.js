import React, {useState} from "react";
import Navbar from "./Navbar"; // Hoặc Sidebar, Footer, v.v.
import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";
import { userId } from '../utils/utils';
import Footer from "./Footer";
import { Loading } from "./Loading";
import { useLoading } from "../context/LoadingContext";

const Layout = () => {
    const { isLoading } = useLoading();
    console.log(isLoading)
    const [isSidebarExpanded, setIsSidebarExpanded] = useState(false); // Trạng thái Sidebar
    const user_id = userId();
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
                    className={`flex-1 transition-all duration-300 ${user_id ? (isSidebarExpanded ? "ml-64" : "ml-16") : ''}`}
                >
                    <div>
                        <Navbar />
                        <div className="relative">
                            {isLoading && <Loading />}
                            <Outlet />
                        </div>
                        <Footer />
                    </div>
                </div>
            </div>
        </>
    );
};

export default Layout;