// xử lý xóa và thêm trang chi tiết phim
const favoriteBtnEl = document.querySelector(".favorite-btn");

// xử lý toggle yêu thích
const handleFavoriteToggle = async () => {
    // kiểm tra người dùng đã đăng nhập chưa
    if (currentUser !== null) {
        // kiểm tra nếu đã có trong phim yêu thích thì mới được xóa và ngược lại
        if (favorite !== null) {
            await removeFromFavorite(favorite.id);
        } else {
            await addToFavorite();
        }
    } else {
        toastr.info("Cần phải đăng nhập để thêm vào danh sách yêu thích");
    }
}

//  thêm phim vào danh sách yêu thích
const addToFavorite = async () => {
    const data = {
        userId: currentUser.id,
        movieId: movie.id
    }
    try {
        let res = await axios.post("/api/favorites", data);
        toastr.success("Đã thêm vào danh sách yêu thích");
        favoriteBtnEl.innerHTML = 'Bỏ Yêu Thích <i class="fa-solid fa-heart"></i>';
        favorite = {id: res.data.id}
        console.log(favorite)
    } catch (error) {
        console.log(error);
        toastr.error(error.response.data.message)
    }
}

// xóa phim khỏi danh sách yêu thích
const removeFromFavorite = async (id) => {
    const confirmDelete = window.confirm("Bạn có chắc muốn xóa khỏi danh sách yêu thích không?");
    if (confirmDelete) {
        try {
            await axios.delete(`/api/favorites/${id}`);
            toastr.success("Đã xóa khỏi danh sách yêu thích thành công");
            favoriteBtnEl.innerHTML = 'Yêu Thích <i class="fa-solid fa-heart"></i>';
            favorite = null;
            console.log(favorite)
        } catch (error) {
            console.log(error);
            toastr.error(error.response.data.message)
        }
    }
}

// Lắng nghe sự kiện click trên nút yêu thích
if (favoriteBtnEl) {
    favoriteBtnEl.addEventListener("click", handleFavoriteToggle);
}


// xử lý nút xóa trang phim yêu thích
document.addEventListener("DOMContentLoaded", function() {
    // Chọn tất cả các nút xóa yêu thích
    const deleteFavoriteButtons = document.querySelectorAll(".delete-favorite-btn");


    const handleDeleteFavorite = async (event) => {
        event.preventDefault();
        event.stopPropagation();

        const button = event.currentTarget;
        const favoriteId = button.getAttribute("data-favorite-id");

        // Kiểm tra xem favoriteId có phải là null hay undefined
        if (!favoriteId) {
            toastr.error("ID yêu thích không hợp lệ");
            return;
        }

        const confirmDelete = window.confirm("Bạn có chắc muốn xóa khỏi danh sách yêu thích không?");
        if (confirmDelete) {
            try {
                await axios.delete(`/api/favorites/${favoriteId}`);
                toastr.success("Đã xóa khỏi danh sách yêu thích thành công");

                // Xóa phần tử khỏi DOM sau khi xóa thành công
                const movieItem = button.closest(".col-3");
                if (movieItem) {
                    movieItem.remove();
                }
            } catch (error) {
                console.log(error);
                toastr.error(error.response.data.message);
            }
        }
    };

    // Gán sự kiện click cho từng nút xóa yêu thích
    deleteFavoriteButtons.forEach(button => {
        button.addEventListener("click", handleDeleteFavorite);
    });
});

