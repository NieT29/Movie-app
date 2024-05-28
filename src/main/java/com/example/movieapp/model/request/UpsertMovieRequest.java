package com.example.movieapp.model.request;

import com.example.movieapp.model.enums.MovieType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertMovieRequest {
    @NotEmpty(message = "Tên phim không được để trống")
    String name;
    @NotEmpty(message = "Mô tả không được để trống")
    String description;
    @NotNull(message = "Năm phát hành không được để trống")
    Integer releaseYear;
    MovieType type;
    Boolean status;
    @NotEmpty(message = "Trailer không được để trống")
    String trailer;
    Integer countryId;
    List<Integer> genreIds;
    List<Integer> actorIds;
    List<Integer> directorIds;
}
