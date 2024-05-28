package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Actor;
import com.example.movieapp.repository.ActorRepository;
import com.example.movieapp.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
    @Autowired
    private ActorRepository actorRepository;

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public List<Actor> getActorsById(List<Integer> ids) {
        return actorRepository.findAllById(ids);
    }
}
