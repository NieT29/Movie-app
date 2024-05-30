package com.example.movieapp.service.impl;

import com.example.movieapp.entity.*;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.model.request.UpsertMovieRequest;
import com.example.movieapp.repository.*;
import com.example.movieapp.service.*;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorService actorService;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private FileService fileService;

    @Override
    public List<Movie> getMovieByType(MovieType movieType, Boolean status, Sort sort) {
        return movieRepository.findByTypeAndStatus(movieType, status, sort);
    }

    public Page<Movie> getMovieByType(MovieType movieType, Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page -1, pageSize, Sort.by("createdAt").descending());
        return movieRepository.findByTypeAndStatus(movieType, status, pageRequest);
    }

    public Page<Movie> getHotMovies(Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page -1, pageSize);
        return movieRepository.findByStatusOrderByRatingDesc(status, pageRequest);
    }

    public Movie getMovie(Integer id, String slug, Boolean status) {
        return movieRepository.findByIdAndSlugAndStatus(id, slug, status);
    }

    @Override
    public List<Movie> getRelateMovies(Integer id, String genreName, Boolean status) {
        return movieRepository.findByGenres_NameAndStatusAndIdNotOrderByRatingDesc(genreName, status, id).stream()
                .limit(6)
                .toList();
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll(Sort.by("createdAt").descending());
    }

    @Override
    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));
    }

    @Override
    public Movie createMovie(UpsertMovieRequest request) {
        Slugify slugify = Slugify.builder().build();

        Movie movie = Movie.builder()
                .name(request.getName())
                .poster("https://placehold.co/600x400?text=" + String.valueOf(request.getName().charAt(0)).toUpperCase())
                .slug(slugify.slugify(request.getName()))
                .description(request.getDescription())
                .releaseYear(request.getReleaseYear())
                .type(request.getType())
                .status(request.getStatus())
                .trailer(request.getTrailer())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .actors(actorService.getActorsById(request.getActorIds()))
                .directors(directorService.getDirectorsById(request.getDirectorIds()))
                .genres(genreService.getGenresById(request.getGenreIds()))
                .country(countryService.getCountryById(request.getCountryId()))
                .build();
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(Integer id, UpsertMovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found"));

        Slugify slugify = Slugify.builder().build();
        movie.setName(request.getName());
        movie.setSlug(slugify.slugify(request.getName()));
        movie.setDescription(request.getDescription());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setType(request.getType());
        movie.setStatus(request.getStatus());
        movie.setTrailer(request.getTrailer());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setActors(actorService.getActorsById(request.getActorIds()));
        movie.setDirectors(directorService.getDirectorsById(request.getDirectorIds()));
        movie.setGenres(genreService.getGenresById(request.getGenreIds()));
        movie.setCountry(countryService.getCountryById(request.getCountryId()));
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }

    @Override
    public String uploadPoster(Integer id, MultipartFile file) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found"));

        try {
            Map data = fileService.uploadFile(file);
            String url = (String) data.get("url");
            movie.setPoster(url);
            movieRepository.save(movie);

            return url;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file");
        }
    }
}
