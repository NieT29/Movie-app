package com.example.movieapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {
    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    String oldPassword;
    @NotEmpty(message = "Mật khẩu mới không được để trống")
    String newPassword;
    @NotEmpty(message = "Xác nhận mật khẩu không được để trống")
    String confirmPassword;
}
