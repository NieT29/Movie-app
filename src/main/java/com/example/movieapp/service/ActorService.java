package com.example.movieapp.service;

import com.example.movieapp.entity.Actor;

import java.util.List;

public interface ActorService {
    List<Actor> getAllActors();
    List<Actor> getActorsById(List<Integer> ids);
}
