package com.example.movieapp.entity;

import com.example.movieapp.model.enums.MovieType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String slug;

    @Column(columnDefinition = "TEXT")
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
