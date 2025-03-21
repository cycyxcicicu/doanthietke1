import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dangki from './page/Dangki';
import Login from './page/Login';
import Trangchu from './page/Trangchu';
import Taocauhoi from './page/Taocauhoi';
import Taobaikiemtra from './page/CreateQuiz';
import ProtectedRoute from './ProtectedRoute';
import BaiKiemTraPage from './page/baikiemtra';
import Lambaikiemtra from './page/Lambaikiemtra';

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
