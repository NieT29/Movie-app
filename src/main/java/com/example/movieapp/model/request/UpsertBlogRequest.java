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
public class UpsertBlogRequest {
    @NotEmpty(message = "Tiêu đề không được để trống")
    String title;
    @NotEmpty(message = "Nội dung không được để trống")
    String content;
    @NotEmpty(message = "Mô tả không được để trống")
    String description;
    Boolean status;
}

