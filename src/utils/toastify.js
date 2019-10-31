import { toast } from 'react-toastify';

const options = {
    autoClose: 3000
};

export const ToastSuccess = (message) => {
    toast.success(message, options)
}

export const ToastError = (message) => {
    toast.error(message, options)
}

export const ToastWarning = (message) => {
    toast.warn(message, options)
}