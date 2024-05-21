package com.example.movieapp.service;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.entity.User;
import com.example.movieapp.model.request.FavoriteRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findByUser_IdOrderByCreatedAtDesc();

    Favorite findFavoriteForCurrentUserAndMovie(Integer movieId);


    Favorite addToFavorite(FavoriteRequest request);

    void removeFromFavorite(Integer id);
}
