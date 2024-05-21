package com.example.movieapp.rest;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.model.request.FavoriteRequest;
import com.example.movieapp.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/favorites")
public class FavoriteApi {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<?> addToFavorite(@RequestBody FavoriteRequest request) {
        Favorite favorite = favoriteService.addToFavorite(request);
        return new ResponseEntity<>(favorite, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFromFavorite(@PathVariable Integer id) {
        favoriteService.removeFromFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
