package com.example.movieapp.service;

import com.example.movieapp.entity.Review;
import com.example.movieapp.model.request.UpsertReviewRequest;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewListOfMovie(Integer movieId);

    Review createReview(UpsertReviewRequest request);

    Review updateReview(UpsertReviewRequest request, Integer id);

    void deleteReview(Integer id);

}
