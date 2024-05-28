package com.example.movieapp.controller;

import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class MovieController {
    private final ActorService actorService;
    private final CountryService countryService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final MovieService movieService;
    private final EpisodeService episodeService;

    @GetMapping
    public String getIndexPage(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("directors", directorService.getAllDirectors());
        model.addAttribute("actors", actorService.getAllActors());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("movieTypes", MovieType.values());
        return "admin/movie/create";
    }


    @GetMapping("/{id}")
    public String getDetailPage(Model model, @PathVariable Integer id) {
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("directors", directorService.getAllDirectors());
        model.addAttribute("actors", actorService.getAllActors());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("movieTypes", MovieType.values());
        model.addAttribute("movie", movieService.getMovieById(id));

        // trả về ds tập phim của movie (sắp xếp theo display order tâng dần)
        model.addAttribute("episodes", episodeService.getEpisodeListOfMovie(id));
        return "admin/movie/detail";
    }
}
