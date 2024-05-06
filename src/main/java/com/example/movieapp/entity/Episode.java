package com.example.movieapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    Double duration;

    @Column(nullable = false)
    String videoUrl;

    Integer episodeOrder;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
