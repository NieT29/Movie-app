package com.example.movieapp.service;

import com.example.movieapp.entity.Country;
import com.example.movieapp.entity.Director;

import java.util.List;

public interface DirectorService {
    List<Director> getAllDirectors();
    List<Director> getDirectorsById(List<Integer> ids);

}
