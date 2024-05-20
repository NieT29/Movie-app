package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.Review;
import com.example.movieapp.entity.User;
import com.example.movieapp.exception.BadRequestException;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.UpsertReviewRequest;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.repository.ReviewRepository;
import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.service.ReviewService;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private HttpSession session;

    @Override
    public List<Review> getReviewListOfMovie(Integer movieId) {
        return reviewRepository.findByMovie_IdOrderByCreatedAtDesc(movieId);
    }

    public Review createReview(UpsertReviewRequest request) {
        User user = (User) session.getAttribute("currentUser");

        // kiểm tra xem movie có tồn tại hay không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        User user = (User) session.getAttribute("currentUser");


        // kiểm tra xem movie có tồn tại hay không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // kiểm tra xem review này có phải của user hay không
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Not review's owner");
        }


        // kiểm tra xem review này có thuộc movie hay không
        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new BadRequestException("Not review's movie");
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
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        User user = (User) session.getAttribute("currentUser");


        // kiểm tra xem review này có phải của user hay không
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Not review's owner");
        }

        reviewRepository.delete(review);
    }


}
