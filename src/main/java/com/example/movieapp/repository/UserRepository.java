package com.example.movieapp.repository;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.User;
import com.example.movieapp.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRole(UserRole role);

    Optional<User> findByEmail(String email);

    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endOfMonth);

}
