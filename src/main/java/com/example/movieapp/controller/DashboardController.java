package com.example.movieapp.controller;

import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.service.BlogService;
import com.example.movieapp.service.MovieService;
import com.example.movieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final MovieService movieService;
    private final UserService userService;
    private final BlogService blogService;

    @GetMapping
    public String getDashboardPage(Model model) {
        model.addAttribute("moviesInMonth", movieService.getMoviesCreatedInMonth());
        model.addAttribute("allMovies", movieService.getAllMovies());
        model.addAttribute("usersInMonth", userService.getUsersCreatedInMonth());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("blogsInMonth", blogService.getBlogsCreatedInMonth());
        model.addAttribute("allBlogs", blogService.getAllBlogs());
        return "admin/dashboard/dashboard";
    }
}
