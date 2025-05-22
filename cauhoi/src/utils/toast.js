import { toast } from "react-toastify";
export const showError = (message) => {
    toast.error(message, {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
    });
}
export const showSuccess = (message) => {

}
