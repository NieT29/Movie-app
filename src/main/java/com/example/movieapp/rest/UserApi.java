package com.example.movieapp.rest;

import com.example.movieapp.model.request.UpdatePasswordRequest;
import com.example.movieapp.model.request.UpdateProfileUserRequest;
import com.example.movieapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApi {
    @Autowired
    private UserService userService;

    // sửa thông tin user
    @PutMapping("update-profile")
    public ResponseEntity<?> updateInfor(@Valid @RequestBody UpdateProfileUserRequest request) {
        userService.updateNameUser(request);
        return ResponseEntity.ok("Update successfully");
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.ok("Update successfully");
    }
}
