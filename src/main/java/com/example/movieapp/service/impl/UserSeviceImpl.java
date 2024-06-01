package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Review;
import com.example.movieapp.entity.User;
import com.example.movieapp.exception.BadRequestException;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.UpdatePasswordRequest;
import com.example.movieapp.model.request.UpdateProfileUserRequest;
import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserSeviceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession session;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public void updateNameUser(UpdateProfileUserRequest request) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new BadRequestException("User not logged in");
        }

        User user = userRepository.findById(currentUser.getId()).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        currentUser.setName(request.getName());
        userRepository.save(currentUser);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new BadRequestException("User not logged in");
        }

        // kiểm tra mật khẩu cũ có khớp với mật khẩu hiện tại không
        if (!passwordEncoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            throw new BadRequestException("The old password is incorrect");
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Password and Confirm Password do not match");
        }

        // kiểm tra mật khẩu cũ và mật khẩu mới có trùng nhau không
        if (passwordEncoder.matches(request.getNewPassword(), currentUser.getPassword())) {
            throw new BadRequestException("The new password cannot be the same as the old password");
        }

        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(currentUser);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersCreatedInMonth() {
        LocalDateTime startDate = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now();
        return userRepository.findUsersCreatedBetween(startDate, endDate);
    }
}
