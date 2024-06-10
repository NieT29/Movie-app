package com.example.movieapp.service;

import com.example.movieapp.entity.User;
import com.example.movieapp.model.request.LoginRequest;
import com.example.movieapp.model.request.RegisterRequest;

public interface AuthService {
    void login(LoginRequest request);

    User register(RegisterRequest request);

//    void logout();
}
