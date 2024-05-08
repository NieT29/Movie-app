package com.example.movieapp.service;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.enums.MovieType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieService {
   List<Movie> getMovieByType(MovieType movieType, Boolean status, Sort sort);
}
