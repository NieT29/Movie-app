package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Episode;
import com.example.movieapp.repository.EpisodeRepository;
import com.example.movieapp.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    @Autowired
    private EpisodeRepository episodeRepository;


    @Override
    public List<Episode> getEpisodeListOfMovie(Integer movieId) {
        return episodeRepository.findByMovie_IdOrderByEpisodeOrder(movieId);
    }
}
