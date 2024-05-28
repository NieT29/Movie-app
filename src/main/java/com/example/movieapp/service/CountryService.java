package com.example.movieapp.service;

import com.example.movieapp.entity.Actor;
import com.example.movieapp.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();
    Country getCountryById(Integer id);
}
