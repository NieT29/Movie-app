package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Episode;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.exception.BadRequestException;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.UpsertEpisodeRequest;
import com.example.movieapp.repository.EpisodeRepository;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.service.EpisodeService;
import com.example.movieapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EpisodeServiceImpl implements EpisodeService {
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private FileService fileService;

    @Override
    public List<Episode> getEpisodeListOfMovie(Integer movieId) {
        return episodeRepository.findByMovie_IdOrderByEpisodeOrderAsc(movieId);
    }

    @Override
    public Episode createEpisode(UpsertEpisodeRequest request) {

        // kiểm tra xem movie có tồn tại hay không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // tạo tập phim
        Episode episode = Episode.builder()
                .name(request.getName())
                .episodeOrder(request.getEpisodeOrder())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .videoUrl("https://teacher.techmaster.vn/ded8c44b-aa96-4c22-8611-17ae8c9e7555")
                .movie(movie)
                .build();
        episodeRepository.save(episode);
        return episode;
    }

    @Override
    public Episode updateEpisode(UpsertEpisodeRequest request, Integer id) {
        // kiểm tra xem tập phim có tồn tại không
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode not found"));

        // kiểm tra xem movie có tồn tại hay không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // kiểm tra xem episode này có thuộc movie hay không
        if (!episode.getMovie().getId().equals(movie.getId())) {
            throw new BadRequestException("Not episode's movie");
        }

        // cập nhật tập phim
        episode.setName(request.getName());
        episode.setEpisodeOrder(request.getEpisodeOrder());
        episode.setUpdatedAt(LocalDateTime.now());
        episodeRepository.save(episode);
        return episode;
    }

    @Override
    public void deleteEpisode(Integer id) {
        // kiểm tra xem tập phim có tồn tại hay không
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode not found"));
        episodeRepository.delete(episode);
    }

    @Override
    public Map uploadVideo(Integer id, MultipartFile file) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode not found"));
        try {
            Map data = fileService.uploadVideo(file);
            String url = (String) data.get("url");
            Double durationDouble = (Double) data.get("duration");
            Integer duration = durationDouble.intValue();
            episode.setVideoUrl(url);
            episode.setDuration(duration);
            episodeRepository.save(episode);

            return data;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading video");
        }
    }
}
