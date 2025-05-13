import { Navigate, Outlet  } from 'react-router-dom';
import { isTokenValid } from '../utils/utils';

const ProtectedRoute = () => {
  return isTokenValid() ? <Outlet /> : <Navigate to="/dangnhap" replace />;
};

const UnProtectedRoute = ({children}) => {
  return isTokenValid() ?  <Navigate to="/" replace /> : children;
};


export {ProtectedRoute, UnProtectedRoute};
