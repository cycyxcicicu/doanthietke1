import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dangki from './pages/Dangki';
import Login from './pages/Login';
import Trangchu from './pages/Trangchu';
import Taocauhoi from './pages/Taocauhoi';
import ProtectedRoute from './ProtectedRoute';
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import Layout from './components/Layout';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Các route không cần xác thực */}
          <Route path="/" element={<Login />} />
          <Route path="/dangki" element={<Dangki />} />
          <Route path="/dangnhap" element={<Login />} />

          {/* Các route cần xác thực */}
          <Route
            element={
              <ProtectedRoute>
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route path="/trangchu" element={<Trangchu />} />
            <Route path="/taocauhoi" element={<Taocauhoi />} />
          </Route>
        </Routes>
      </BrowserRouter>
      <ToastContainer />
    </>
  );
}

export default App;
