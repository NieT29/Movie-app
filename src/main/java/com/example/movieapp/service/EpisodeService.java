package com.example.movieapp.service;

import com.example.movieapp.entity.Episode;

import java.util.List;

public interface EpisodeService {
    List<Episode> getEpisodeListOfMovie(Integer movieId);
}
