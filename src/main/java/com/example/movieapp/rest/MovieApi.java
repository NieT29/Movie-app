package com.example.movieapp.rest;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.request.UpsertBlogRequest;
import com.example.movieapp.model.request.UpsertMovieRequest;
import com.example.movieapp.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/movies")
public class MovieApi {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody UpsertMovieRequest request) {
        Movie movie = movieService.createMovie(request);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@Valid @RequestBody UpsertMovieRequest request, @PathVariable Integer id) {
        Movie movie = movieService.updateMovie(id, request);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-poster")
    public ResponseEntity<?> upLoadPoster(@PathVariable Integer id,
                                             @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(movieService.uploadPoster(id, file));
    }
}
