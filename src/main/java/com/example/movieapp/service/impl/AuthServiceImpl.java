package com.example.movieapp.service.impl;

import com.example.movieapp.entity.User;
import com.example.movieapp.model.enums.UserRole;
import com.example.movieapp.model.request.LoginRequest;
import com.example.movieapp.model.request.RegisterRequest;
import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;

    @Override
    public void login(LoginRequest request) {
        // kiểm tra email xem co ton tai trong database khong
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email  incorrect"));

        // kiem ra xem password có khớp với password trong database khong
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }

        // luu thông tin user vao trong session de su dung o cac request tiep theo
        session.setAttribute("currentUser", user);
    }

    public User register(RegisterRequest request) {
        //kiểm tra email đã tồn tại hay chưa để đăng ký
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // kiểm tra password có trung với confirmPassword hay không
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .avatar("https://placehold.co/600x400?text=" + String.valueOf(request.getName().charAt(0)).toUpperCase())
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return user;
    }
}
