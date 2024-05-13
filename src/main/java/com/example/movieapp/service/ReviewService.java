package com.example.movieapp.service;

import com.example.movieapp.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewListOfMovie(Integer movieId);
}
