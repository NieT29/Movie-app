package com.example.movieapp.entity;

import com.example.movieapp.model.enums.MovieType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String slug;

    String description;

    String poster;

    @Column(nullable = false)
    Integer releaseYear;

    Double rating;

    @Enumerated(EnumType.STRING)
    MovieType type;

    Boolean status;

    String trailer;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
