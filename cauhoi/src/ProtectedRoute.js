import { Navigate } from 'react-router-dom';
import { isTokenValid } from './utils/utils';

const ProtectedRoute = ({ children }) => {
  return isTokenValid() ? children : <Navigate to="/dangnhap" replace />;
};

export default ProtectedRoute;
