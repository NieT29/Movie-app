package com.example.movieapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileUserRequest {
    @NotEmpty(message = "Tên không được để trống")
    String name;
}
