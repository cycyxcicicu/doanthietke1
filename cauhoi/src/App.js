import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Trangchu from './pages/Trangchu';
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import Layout from './components/Layout';
import NotFound from './pages/errors/NotFound';
import { LoadingProvider } from './context/LoadingContext';
import TronDe from './pages/exams/tronde';

function App() {
  return (
    <>
     <LoadingProvider>
      <BrowserRouter>
        <Routes>
          <Route
              element={<Layout />}
            >
            <Route path="/trangchu" element={<Trangchu />} />
            <Route path="/" element={<Trangchu />} />
            <Route path='/cau-hinh' element={<TronDe />} />
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
