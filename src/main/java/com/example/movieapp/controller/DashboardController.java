package com.example.movieapp.controller;

import com.example.movieapp.service.BlogService;
import com.example.movieapp.service.MovieService;
import com.example.movieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final MovieService movieService;
    private final UserService userService;
    private final BlogService blogService;

    @GetMapping
    public String getDashboardPage(Model model) {
        model.addAttribute("moviesCountByMonth", movieService.getMoviesCreatedByMonth());
        model.addAttribute("allMovies", movieService.getAllMovies().size());
        Map<String, Integer> moviesData5Month = movieService.getMoviesCountForLastFiveMonths();
        model.addAttribute("moviesData5Month", moviesData5Month);

        model.addAttribute("usersCountByMonth", userService.getUsersCreatedByMonth());
        model.addAttribute("allUsers", userService.getAllUsers().size());
        Map<String, Integer> usersData5Month = userService.getUsersCountForLastFiveMonths();
        model.addAttribute("usersData5Month", usersData5Month);

        model.addAttribute("blogsCountByMonth", blogService.getBlogsCreatedByMonth());
        model.addAttribute("allBlogs", blogService.getAllBlogs().size());
        return "admin/dashboard/dashboard";
    }
}
