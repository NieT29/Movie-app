package com.example.movieapp.service;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.model.request.UpsertMovieRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MovieService {
   List<Movie> getMovieByType(MovieType movieType, Boolean status, Sort sort);
   Page<Movie> getMovieByType(MovieType movieType, Boolean status, int page, int pageSize);
   Page<Movie> getHotMovies(Boolean status, int page, int pageSize);
   Movie getMovie(Integer id, String slug, Boolean status);
   List<Movie> getRelateMovies(Integer id, String genreName, Boolean status);
   List<Movie> getAllMovies();

   Movie createMovie(UpsertMovieRequest request);

    Movie getMovieById(Integer id);

   Movie updateMovie(Integer id, UpsertMovieRequest request);

   void deleteMovie(Integer id);

   String uploadPoster(Integer id, MultipartFile file);

   List<Movie> getMoviesCreatedByMonth();

   Map<String, Integer> getMoviesCountForLastFiveMonths();
}
