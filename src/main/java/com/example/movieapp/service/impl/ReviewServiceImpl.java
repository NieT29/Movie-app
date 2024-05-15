package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.Review;
import com.example.movieapp.entity.User;
import com.example.movieapp.model.request.UpsertReviewRequest;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.repository.ReviewRepository;
import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Review> getReviewListOfMovie(Integer movieId) {
        return reviewRepository.findByMovie_IdOrderByCreatedAtDesc(movieId);
    }

    // TODO: Validate thong tin: content, rating,... su dung thu vien validate

    public Review createReview(UpsertReviewRequest request) {
        // TODO: fix userID. Về sau userId chính là user đang login
        Integer userId = 3;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // kiểm tra xem movie có tồn tại hay không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // tạo review
        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .movie(movie)
                .user(user)
                .build();

        reviewRepository.save(review);
        return review;
    }

    public Review updateReview(UpsertReviewRequest request, Integer id) {
        // kiểm tra xem review có tồn tại hay không
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // TODO: fix userID. Về sau userId chính là user đang login
        Integer userId = 3;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // kiểm tra xem movie có tồn tại hay không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // kiểm tra xem review này có phải của user hay không
        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not review's owner");
        }


        // kiểm tra xem review này có thuộc movie hay không
        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new RuntimeException("Not review's movie");
        }

        // cập nhật review
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        return review;
    }

    public void deleteReview(Integer id) {
        // kiểm tra xem review có tồn tại hay không
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // TODO: fix userID. Về sau userId chính là user đang login
        Integer userId = 3;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // kiểm tra xem review này có phải của user hay không
        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not review's owner");
        }

        reviewRepository.delete(review);
    }


}
