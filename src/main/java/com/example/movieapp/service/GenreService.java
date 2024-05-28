package com.example.movieapp.service;

import com.example.movieapp.entity.Director;
import com.example.movieapp.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
    List<Genre> getGenresById(List<Integer> ids);

}
