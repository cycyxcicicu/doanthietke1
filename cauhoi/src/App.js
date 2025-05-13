import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dangki from './pages/auth/Dangki';
import Login from './pages/auth/Login';
import Trangchu from './pages/Trangchu';
import {UnProtectedRoute, ProtectedRoute} from './router/ProtectedRoute';
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import Layout from './components/Layout';
import NotFound from './pages/errors/NotFound';
import QuestionIndex from './pages/questions/index';
import SettingIndex from './pages/setting/SettingsPage';
import ExamCreate from './pages/exams/create';
import QuestionCreate from './pages/questions/Taocauhoi';
import QuestionCourse from './pages/questions/course';
import QuestionEdit from './pages/questions/edit';
import { LoadingProvider } from './context/LoadingContext';
import TronDe from './pages/exams/tronde';

function App() {
  return (
    <>
     <LoadingProvider>
      <BrowserRouter>
        <Routes>
          {/* Các route không cần xác thực */}
          <Route path="/dangki" element={<UnProtectedRoute><Dangki /></UnProtectedRoute>} />
          <Route path="/dangnhap" element={<UnProtectedRoute><Login /></UnProtectedRoute>} />

          {/* Các route cần xác thực */}
          <Route
              element={<Layout />}
            >
            <Route path="/trangchu" element={<Trangchu />} />
            <Route path="/" element={<Trangchu />} />
            <Route path='/cau-hinh/:id' element={<TronDe />} />
            <Route
              element={<ProtectedRoute />}
            >
              <Route path="/questions" element={<QuestionIndex />} />
              <Route path="/questions/create" element={<QuestionCreate />} />
              <Route path="/questions/course" element={<QuestionCourse />} />
              <Route path="/questions/edit" element={<QuestionEdit />} />
              <Route path="/setting" element={<SettingIndex />} />
              <Route path="/exams/create" element={<ExamCreate />} />
            </Route>  
           </Route>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
      <ToastContainer />
     </LoadingProvider>
    </>
  );
}

export default App;
