package com.example.movieapp.service;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.User;
import com.example.movieapp.model.request.UpdatePasswordRequest;
import com.example.movieapp.model.request.UpdateProfileUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void updateNameUser(UpdateProfileUserRequest request);


    void updatePassword(UpdatePasswordRequest request);

    List<User> getAllUsers();
    List<User> getUsersCreatedInMonth();
}
