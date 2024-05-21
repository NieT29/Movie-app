package com.example.movieapp.repository;

import com.example.movieapp.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUser_IdOrderByCreatedAtDesc(Integer userId);


    Favorite findByUser_IdAndMovie_Id(Integer userId, Integer movieId);

    List<Favorite> findByMovie_id(Integer movieId);
}
