package com.example.movieapp.rest;

import com.example.movieapp.entity.Review;
import com.example.movieapp.model.request.UpsertReviewRequest;
import com.example.movieapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
// api trả về json nên có /api
public class MovieApi {
    private final ReviewService reviewService;

    // Tạo review
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody UpsertReviewRequest request) {
        Review review = reviewService.createReview(request);
        return new ResponseEntity<>(review, HttpStatus.CREATED); // 201
    }

    // Cập nhật review
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@RequestBody UpsertReviewRequest request, @PathVariable Integer id) {
        Review review = reviewService.updateReview(request, id);
        return ResponseEntity.ok(review); // 200
    }

    // Xóa review
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
