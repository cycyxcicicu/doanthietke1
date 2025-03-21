import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { isTokenValid } from './utils';

const ProtectedRoute = ({ children }) => {
  const [isValid, setIsValid] = useState(null);

  useEffect(() => {
    const checkToken = async () => {
      const valid = await isTokenValid();
      setIsValid(valid);
    };

    checkToken();
  }, []);

  if (isValid === null) {
    return <div>Loading...</div>; // Hiển thị trong khi kiểm tra token
  }

  return isValid ? children : <Navigate to="/dangnhap" replace />;
};

export default ProtectedRoute;
