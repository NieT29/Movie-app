const stars = document.querySelectorAll(".star1");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        const rating = parseInt(star.getAttribute("data-rating"));
        highlightStars(rating);
    });

    star.addEventListener("mouseout", () => {
        resetStars();
        highlightStars(currentRating);
    });

    star.addEventListener("click", () => {
        currentRating = parseInt(star.getAttribute("data-rating"));
        ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
        highlightStars(currentRating);
    });
});

function resetStars() {
    stars.forEach((star) => {
        star.classList.remove("active");
    });
}

function highlightStars(rating) {
    stars.forEach((star) => {
        const starRating = parseInt(star.getAttribute("data-rating"));
        if (starRating <= rating) {
            star.classList.add("active");
        }
    });
}

// render review
const formatDate = dateStr => {
    const date = new Date(dateStr);
    const day = `0${date.getDate()}`.slice(-2);
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear()
    return `${day}/${month}/${year}`;
}
const reviewListEl = document.querySelector(".review-list")
const renderReview = reviews => {
    let html = "";
    reviews.forEach(review => {
        if (currentUser.id === review.user.id) {
            html += `
                <div class="text-white d-flex flex-column mb-3 p-3 user-review rounded-2 ">
                    <div class="d-flex align-items-center pb-2" >
                        <img src="${review.user.avatar}" class=" avatar me-2 " alt="${review.user.name}">
                        <p class="m-0 pe-2 review-name">${review.user.name}</p>
                        <p class="m-0 review-time">${formatDate(review.createdAt)}</p>
                    </div>
                    <div>
                        <p class="m-0 review-text" >${review.content}</p>
                    </div>
                    <div class="mt-2">
                            <button onclick="deleteReview(${review.id})" type="button" class="btn btn-outline-secondary btn-sm btn-delete" style="--bs-btn-padding-y: .2rem; --bs-btn-padding-x: .3rem; --bs-btn-font-size: .7rem;">Xóa</button>
                            <button onclick="openEditModal(${review.id})" type="button" class="btn btn-outline-secondary btn-sm" style="--bs-btn-padding-y: .2rem; --bs-btn-padding-x: .3rem; --bs-btn-font-size: .7rem;">Sửa</button>
                    </div>
                </div>
            `
        } else {
            html += `
                <div class="text-white d-flex flex-column mb-3 p-3 user-review rounded-2 ">
                    <div class="d-flex align-items-center pb-2" >
                        <img src="${review.user.avatar}" class=" avatar me-2 " alt="${review.user.name}">
                        <p class="m-0 pe-2 review-name">${review.user.name}</p>
                        <p class="m-0 review-time">${formatDate(review.createdAt)}</p>
                    </div>
                    <div>
                        <p class="m-0 review-text" >${review.content}</p>
                    </div>
                </div>
            `
        }


    })

    reviewListEl.innerHTML = html;
}

const formReviewEl = document.getElementById("form-review");
const reviewContentEl = document.getElementById("review-content")
const modalReviewEl = document.getElementById("modal-review");

let idReviewEdit = null;
const myModalReviewEl = new bootstrap.Modal(modalReviewEl, {
    keyboard: false
})

const btnOpenModal = document.querySelector(".btn-open-modal")
btnOpenModal.addEventListener("click", () => {
    console.log("mở")
    myModalReviewEl.show();
    modalReviewTitleEl.textContent = `Viết đánh giá`;
    btnCreateReviewEl.textContent = `Tạo đánh giá`

});

// đóng modal
modalReviewEl.addEventListener('hidden.bs.modal', event => {
    console.log("Su kien dong modal")
    currentRating = 0;
    reviewContentEl.value = "";
    ratingValue.textContent = "";
    resetStars();
    idReviewEdit = null;
})

// validate form
$('#form-review').validate({
    rules: {
        content: {
            required: true,
        }
    },
    messages: {
        content: {
            required: "Vui lòng nhập nội dung đánh giá"
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

// submit form
formReviewEl.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Nếu form invalid thì return
    if (!$("#form-review").valid()) {
        return;
    }

    if (currentRating === 0) {
        toastr.warning("Vui lòng chọn số sao")
        return;
    }

    const data = {
        content: reviewContentEl.value,
        rating: currentRating,
        movieId: movie.id
    }

    if (idReviewEdit) {
        await editReview(data)
    } else {
        await createReview(data)
    }
})

// tạo review
const createReview = async (data) => {
    try {
        let res = await axios.post("/api/reviews", data)
        reviews.unshift(res.data);
        renderReview(reviews)
        // đóng modal
        myModalReviewEl.hide();
        toastr.success("Đánh giá thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
}


// mở modal chỉnh sửa và hiển thị dữ liệu của review đó
const modalReviewTitleEl = document.querySelector(".modal-review-title")
const btnCreateReviewEl = document.getElementById("btn-create-review")

const openEditModal = reviewId => {
    myModalReviewEl.show();
    modalReviewTitleEl.textContent = `Sửa đánh giá`
    btnCreateReviewEl.textContent = `Lưu đánh giá`

    const review = reviews.find(review => review.id === reviewId)

    reviewContentEl.value = review.content;
    currentRating = review.rating;
    ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
    highlightStars(currentRating)
    idReviewEdit = reviewId
}

// sửa review
const editReview = async (data) => {
    try {
        const res = await axios.put(`/api/reviews/${idReviewEdit}`, data)
        const editedReviewIndex = reviews.findIndex(review => review.id === idReviewEdit);
        if (editedReviewIndex !== -1) {
            reviews[editedReviewIndex] = res.data;
            renderReview(reviews);
        }
        myModalReviewEl.hide()
        toastr.success("Sửa đánh giá thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
}

// xóa review
const deleteReview = async (id) => {
    const confirm = window.confirm("Bạn có chắc muốn xóa không?")
    if (!confirm)
        return;

    try {
        const res = await axios.delete(`/api/reviews/${id}`)
        reviews = reviews.filter(review => review.id !== id);
        renderReview(reviews)
        toastr.success("Xóa đánh giá thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
}



