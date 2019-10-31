import Swal from 'sweetalert2';

export const confirmDelete = () => {
    return Swal.fire({
        title: 'Delete this one?',
        text: "This action can not be canceled!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete',
        cancelButtonText: 'No, Cancel'
    })
}
