package com.example.movieapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertEpisodeRequest {
    @NotEmpty(message = "Tên tập phim không được để trống")
    String name;

    @NotNull(message = "Thứ tự tập phim không được để trống")
    Integer episodeOrder;

    Integer movieId;
}
