package com.example.movieapp.controller;

import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/phim-bo")
    public String getPhimBo(Model model) {
        model.addAttribute("movies", movieService.getMovieByType(MovieType.PHIM_BO, true, Sort.by("createdAt").descending()));
        return "phim-bo";
    }

    @GetMapping("/phim-le")
    public String getPhimLe(Model model) {
        model.addAttribute("movies", movieService.getMovieByType(MovieType.PHIM_LE, true, Sort.by("createdAt").descending()));
        return "phim-le";
    }

    @GetMapping("/phim-chieu-rap")
    public String getPhimChieuRap(Model model) {
        model.addAttribute("movies", movieService.getMovieByType(MovieType.PHIM_CHIEU_RAP, true, Sort.by("createdAt").descending()));
        return "phim-chieu-rap";
    }
}
