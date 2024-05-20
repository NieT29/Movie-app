console.log("from mainjs")

toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-right",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

const logoutEl = document.querySelector(".logout")

logoutEl.addEventListener("click", async (e) => {
    e.preventDefault()

    try {
        let res = await axios.post("/api/auth/logout")
        toastr.success("Đăng xuất thành công")
        setTimeout(() => {
            window.location.href = "/";
        }, 1000)
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
})