package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Director;
import com.example.movieapp.repository.DirectorRepository;
import com.example.movieapp.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {
    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public List<Director> getDirectorsById(List<Integer> ids) {
        return directorRepository.findAllById(ids);
    }
}
