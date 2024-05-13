package com.example.movieapp.service;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.enums.MovieType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieService {
   List<Movie> getMovieByType(MovieType movieType, Boolean status, Sort sort);
   Page<Movie> getMovieByType(MovieType movieType, Boolean status, int page, int pageSize);
   Page<Movie> getHotMovies(Boolean status, int page, int pageSize);
   Movie getMovie(Integer id, String slug, Boolean status);
   List<Movie> getRelateMovies(Integer id, String genreName, Boolean status);
}
