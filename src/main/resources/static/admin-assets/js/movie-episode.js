const formUpsertEpisodeEl = document.getElementById("form-upsert-episode")
const episodeNameEl = document.getElementById("episode-name")
const episodeOrderEl = document.getElementById("episode-order")
const modalUpsertEl = document.getElementById("modal-upsert-episode")
const btnOpenModal = document.querySelector(".btn-open-modal")
const modalUpsertEpisodeTitleEl = document.querySelector(".modal-episode-title")
const btnCreateEpisodeEl = document.getElementById("btn-create-episode")
let idEpisodeEdit = null

btnOpenModal.addEventListener("click", () => {
    console.log("mở");
    $('#modal-upsert-episode').modal('show')
    modalUpsertEpisodeTitleEl.textContent = `Tạo tập phim`
    btnCreateEpisodeEl.textContent = `Tạo`
});

// đóng modal
$('#modal-upsert-episode').on('hidden.bs.modal', () => {
    console.log("sự kiện đóng modal")
    episodeNameEl.value = ""
    episodeOrderEl.value = ""
    idEpisodeEdit = null;
});
// validate form
$('#form-upsert-episode').validate({
    rules: {
        episodeName: {
            required: true,
        },
        episodeOrder: {
            required: true,
            digits: true
        }
    },
    messages: {
        episodeName: {
            required: "Vui lòng nhập tên tập phim"
        },
        episodeOrder: {
            required: "Vui lòng nhập thứ tự tập phim",
            digits: "Thứ tự tập phim phải là số"
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

// render episode
const formatDate = dateStr => {
    const date = new Date(dateStr);
    const day = `0${date.getDate()}`.slice(-2); // 01 -> 01, 015 -> 15
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}
const episodeListEl = document.getElementById("episodeList")
const renderEpisode = episodes => {
    let html = ""
    episodes.forEach(episode => {
        html += `
                <tr>
                    <td>${episode.episodeOrder}</td>
                    <td>${episode.name}</td>
                    <td>${episode.videoUrl == null ? '' : episode.videoUrl}</td>
                    <td>${episode.duration == null ? '' : episode.duration}</td>
                    <td>${formatDate(episode.createdAt)}</td>
                    <td>
                        <button class="btn btn-sm btn-primary"
                            onclick="uploadVideo(${episode.id})">
                            <i class="fas fa-upload"></i>
                        </button>
                        <input type="file" class="d-none" id="input-video-${episode.id}">
    
                        <button class="btn btn-sm btn-success"
                            type="button" data-toggle="modal" data-target="#modal-preview"
                            onclick="previewVideo(${episode.id})">
                            <i class="fas fa-play-circle"></i>
                        </button>
                        <button class="btn btn-sm btn-warning text-white"
                            onclick="openEditModal(${episode.id})">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-sm btn-danger"
                            onclick="deleteEpisode(${episode.id})">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </td>
                    
                </tr>
                `
    })
    episodeListEl.innerHTML = html;
}

// submit form
formUpsertEpisodeEl.addEventListener("submit", async (e) => {
    e.preventDefault()

    if (!$("#form-upsert-episode").valid()) {
        return;
    }

    const data = {
        name: episodeNameEl.value,
        episodeOrder: episodeOrderEl.value,
        movieId: movie.id
    }

    if (idEpisodeEdit) {
        await editEpisode(data)
    } else {
        await createEpisode(data)
    }
})

// tạo tập phim
const createEpisode = async (data) => {
    try {
        let res = await axios.post("/api/admin/episodes", data)
        episodes.push(res.data)
        renderEpisode(episodes)

        // đóng modal
        $('#modal-upsert-episode').modal('hide');
        toastr.success("Tạo tập phim thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
}

// mở modal chỉnh sửa và hiển thị dữ liệu của episode đó

const openEditModal = episodeId => {
    $('#modal-upsert-episode').modal('show');
    modalUpsertEpisodeTitleEl.textContent = `Sửa tập phim`
    btnCreateEpisodeEl.textContent = `Lưu`

    const episode = episodes.find(episode => episode.id === episodeId)
    episodeNameEl.value = episode.name;
    episodeOrderEl.value = episode.episodeOrder

    idEpisodeEdit = episodeId
}

// sửa episode
const editEpisode = async (data) => {
    try {
        let res = await axios.put(`/api/admin/episodes/${idEpisodeEdit}`, data)
        const editEpisodeIndex = episodes.findIndex(episode => episode.id === idEpisodeEdit)
        if (editEpisodeIndex !== -1) {
            episodes[editEpisodeIndex] = res.data
            renderEpisode(episodes)
        }
        $('#modal-upsert-episode').modal('hide');
        toastr.success("Sửa tập phim thành công thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
}

// xóa tập phim
const deleteEpisode = async (id) => {
    const confirm = window.confirm("Bạn có chắc muốn xóa không?")
    if (!confirm)
        return;

    try {
        const res = await axios.delete(`/api/admin/episodes/${id}`)
        episodes = episodes.filter(episode => episode.id !== id)
        renderEpisode(episodes)

        toastr.success("xóa tập phim thành công")
    } catch (error) {
        console.log(error)
        toastr.error(error.response.data.message)
    }
}

// upload video
const inputVideoEl = document.getElementById("input-video")
const uploadVideo = (id) => {
    document.getElementById(`input-video-${id}`).click();
    document.getElementById(`input-video-${id}`).addEventListener("change", async (e) => {
        const files = e.target.files

        const formData = new FormData()
        formData.append("file", files[0])

        try {
            let res = await axios.post(`/api/admin/episodes/${id}/upload-video`, formData)
            const index = episodes.findIndex(episode => episode.id === id)
            if (index !== -1) {
                episodes[index].videoUrl = res.data.url
                episodes[index].duration = res.data.duration
                renderEpisode(episodes)
                toastr.success("Cập nhật video thành công")
            }
        } catch (error) {
            console.log(error)
            toastr.error(error.response.data.message)
        }
    })
}

// preview video
const videoEl = document.getElementById("video")
const previewVideo = (id) => {
    const index = episodes.findIndex(episode => episode.id === id);
    if (index !== -1) {
        videoEl.src = episodes[index].videoUrl
        console.log(videoEl.src)
    }
}



