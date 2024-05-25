// đổi tên
const nameInputEl = document.getElementById("name")
const updateProfileForm = document.querySelector(".form-profile");

$('.form-profile').validate({
    rules: {
        name: {
            required: true,
        }
    },
    messages: {
        name: {
            required: "Tên không được để trống"
        }
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

updateProfileForm.addEventListener("submit", async (e) => {
    e.preventDefault()

    if (!$(".form-profile").valid()) {
        return;
    }

    const newName = nameInputEl.value;

    try {
        const res = await axios.put('/api/users/update-profile', {name: newName})
        toastr.success("Cập nhật thông tin thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
})

// đổi mật khẩu

const changePasswordFormEl = document.querySelector(".form-change-password")
const oldPasswordEl = document.getElementById("old-password")
const newPasswordEl = document.getElementById("new-password")
const confirmPassword = document.getElementById("confirmPassword")

$.validator.addMethod("notEqualTo", function(value, element, param) {
    // Kiểm tra giá trị của trường hiện tại không bằng giá trị của trường tham số
    return this.optional(element) || value !== $(param).val();
}, "Mật khẩu mới không được giống với mật khẩu cũ");

$('.form-change-password').validate({
    rules: {
        oldPassword: {
            required: true
        },
        newPassword: {
            required: true,
            notEqualTo: "#old-password"
        },
        confirmPassword: {
            required: true,
            equalTo: '[name="newPassword"]'
        }
    },
    messages: {
        oldPassword: {
            required: "Mật khẩu cũ không được để trống",
        },
        newPassword: {
            required: "Mật khẩu mới không được để trống",
            notEqualTo: "Mật khẩu mới không được giống mật khẩu cũ"
        },
        confirmPassword: {
            required: "Xác nhận mật khẩu không được để trống",
            equalTo: "Xác nhận mật khẩu không khớp"
        }
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

changePasswordFormEl.addEventListener("submit", async (e) => {
    e.preventDefault()

    if (!$('.form-change-password').valid()) {
        return;
    }
    const data = {
        oldPassword: oldPasswordEl.value,
        newPassword: newPasswordEl.value,
        confirmPassword: confirmPassword.value
    }

    try {
        let res = await axios.put('/api/users/update-password', data)
        toastr.success("Đổi mật khẩu thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
})