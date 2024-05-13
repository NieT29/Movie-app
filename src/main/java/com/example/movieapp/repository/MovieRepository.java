package com.example.movieapp.repository;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.enums.MovieType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByName(String name);
    List<Movie> findByNameIgnoreCase(String name);
    List<Movie> findByNameContaining(String keyword);
    List<Movie> findByNameAndSlug(String name, String slug);
    List<Movie> findByRatingBetween(Double min, Double max);
    List<Movie> findByRatingLessThanEqual(Double max);
    List<Movie> findByCreatedAtAfter(LocalDateTime createdAt);
    List<Movie> findByTypeAndStatus(MovieType type, Boolean status, Sort sort);
    Movie findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);
    List<Movie> findByGenres_NameAndStatusAndIdNotOrderByRatingDesc(String genresName, Boolean status, Integer id);


    // sắp xếp
    List<Movie> findByStatus(Boolean status);
    List<Movie> findByType(MovieType type, Sort sort);
    List<Movie> findByTypeOrderByRatingDesc(MovieType type);
    List<Movie> findByTypeOrderByCreatedAtDesc(MovieType type);
    Movie findFirstByTypeOrderByRatingDesc(MovieType type);

    // đếm số lượng
    long countByStatus(Boolean status);

    // kiểm tra tồn tại
    boolean existsByName(String name);

    // phân trang
    Page<Movie> findByStatus(Boolean status, Pageable pageable);
    Page<Movie> findByTypeAndStatus(MovieType movieType, Boolean status, Pageable pageable);
    Page<Movie> findByStatusOrderByRatingDesc(Boolean status, Pageable pageable);
}
