package com.example.movieapp.model.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotEmpty(message = "Tên không được để trống")
    String name;
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    String email;
    @NotEmpty(message = "Mật khẩu không được để trống")
    String password;
    @NotEmpty(message = "Mật khẩu nhập lại không được để trống")
    String confirmPassword;

}
