package com.example.movieapp.service;

import com.example.movieapp.entity.Episode;
import com.example.movieapp.model.request.UpsertEpisodeRequest;
import com.example.movieapp.model.request.UpsertReviewRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface EpisodeService {
    List<Episode> getEpisodeListOfMovie(Integer movieId);

    Episode createEpisode(UpsertEpisodeRequest request);

    Episode updateEpisode(UpsertEpisodeRequest request, Integer id);

    void deleteEpisode(Integer id);

    Map uploadVideo(Integer id, MultipartFile file);

    Episode getEpisode(Integer id, String tap);
}
