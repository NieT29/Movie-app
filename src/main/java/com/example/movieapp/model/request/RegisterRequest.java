package com.example.movieapp.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String name;
    String email;
    String password;
    String confirmPassword;
}
