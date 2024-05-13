package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getMovieByType(MovieType movieType, Boolean status, Sort sort) {
        return movieRepository.findByTypeAndStatus(movieType, status, sort);
    }

    public Page<Movie> getMovieByType(MovieType movieType, Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page -1, pageSize, Sort.by("createdAt").descending());
        return movieRepository.findByTypeAndStatus(movieType, status, pageRequest);
    }

    public Page<Movie> getHotMovies(Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page -1, pageSize);
        return movieRepository.findByStatusOrderByRatingDesc(status, pageRequest);
    }

    public Movie getMovie(Integer id, String slug, Boolean status) {
        return movieRepository.findByIdAndSlugAndStatus(id, slug, status);
    }

    @Override
    public List<Movie> getRelateMovies(Integer id, String genreName, Boolean status) {
        return movieRepository.findByGenres_NameAndStatusAndIdNotOrderByRatingDesc(genreName, status, id).stream()
                .limit(6)
                .toList();
    }
}
