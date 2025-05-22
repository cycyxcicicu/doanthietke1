import { createContext, useContext, useState, useEffect } from "react";
import { setLoadingSetter } from "../controller/LoadingController"; // đường dẫn đúng theo dự án

const LoadingContext = createContext();

export const useLoading = () => useContext(LoadingContext);

export const LoadingProvider = ({ children }) => {
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setLoadingSetter(setIsLoading); // đăng ký hàm khi mount
  }, []);

  return (
    <LoadingContext.Provider value={{ isLoading, setIsLoading }}>
      {children}
    </LoadingContext.Provider>
  );
};
