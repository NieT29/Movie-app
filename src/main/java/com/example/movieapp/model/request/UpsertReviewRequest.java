package com.example.movieapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertReviewRequest {
    @NotEmpty(message = "Nội dung đánh giá không được để trống")
    String content;
    @NotEmpty(message = "Số sao không được để trống")
    Integer rating;
    Integer movieId;
}
