import React, {useState} from "react";
import Navbar from "./Navbar"; // Hoặc Sidebar, Footer, v.v.
import { Outlet } from "react-router-dom";
import { userId } from '../utils/utils';
import Footer from "./Footer";
import { Loading } from "./Loading";
import { useLoading } from "../context/LoadingContext";

const Layout = () => {
    const { isLoading } = useLoading();
    console.log(isLoading);
    const user_id = userId();
    return (
        <>
            <div className="flex h-screen">
                {/* Nội dung chính */}
                <div
                    className={`flex-1 transition-all duration-300`}
                >
                    <div>
                        <Navbar />
                        <div className="relative pt-[60px]">
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