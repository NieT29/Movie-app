package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Genre;
import com.example.movieapp.repository.GenreRepository;
import com.example.movieapp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> getGenresById(List<Integer> ids) {
        return genreRepository.findAllById(ids);
    }
}
