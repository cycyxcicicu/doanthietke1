import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dangki from './pages/Dangki';
import Login from './pages/Login';
import Trangchu from './pages/Trangchu';
import Taocauhoi from './pages/Taocauhoi';
import Taobaikiemtra from './pages/CreateQuiz';
import ProtectedRoute from './ProtectedRoute';
import BaiKiemTraPage from './pages/baikiemtra';
import Lambaikiemtra from './pages/Lambaikiemtra';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Các route không cần xác thực */}
        <Route path="/" element={<Login />} />
        <Route path="/dangki" element={<Dangki />} />
        <Route path="/dangnhap" element={<Login />} />

        {/* Các route cần xác thực */}
        <Route
          path="/trangchu"
          element={
            <ProtectedRoute>
              <Trangchu />
            </ProtectedRoute>
          }
        />
        <Route
          path="/taocauhoi"
          element={
            <ProtectedRoute>
              <Taocauhoi />
            </ProtectedRoute>
          }
        />
        <Route
          path="/taobaikiemtra"
          element={
            <ProtectedRoute>
              <Taobaikiemtra />
            </ProtectedRoute>
          }
        />
        <Route
          path="/baikiemtra"
          element={
            <ProtectedRoute>
              <BaiKiemTraPage />
            </ProtectedRoute>
          }
        />
		    <Route
          path="/lambaikiemtra"
          element={
            <ProtectedRoute>
              <Lambaikiemtra />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
