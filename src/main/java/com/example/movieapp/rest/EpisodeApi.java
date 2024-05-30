package com.example.movieapp.rest;

import com.example.movieapp.entity.Episode;
import com.example.movieapp.entity.Review;
import com.example.movieapp.model.request.UpsertEpisodeRequest;
import com.example.movieapp.model.request.UpsertReviewRequest;
import com.example.movieapp.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/episodes")
public class EpisodeApi {
    private final EpisodeService episodeService;

    @PostMapping
    public ResponseEntity<?> createEpisode(@Valid @RequestBody UpsertEpisodeRequest request) {
        Episode episode = episodeService.createEpisode(request);
        return new ResponseEntity<>(episode, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEpisode(@Valid @RequestBody UpsertEpisodeRequest request, @PathVariable Integer id) {
        Episode episode = episodeService.updateEpisode(request, id);
        return ResponseEntity.ok(episode); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEpisode(@PathVariable Integer id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PostMapping("/{id}/upload-video")
    public ResponseEntity<?> uploadVideo( @PathVariable Integer id,
                                          @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(episodeService.uploadVideo(id,file));
    }
}
