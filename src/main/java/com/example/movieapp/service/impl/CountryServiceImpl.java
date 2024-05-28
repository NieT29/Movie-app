package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Country;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.repository.CountryRepository;
import com.example.movieapp.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;


    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryById(Integer id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + id));
    }
}
