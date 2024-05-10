package com.example.movieapp.controller;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.service.BlogService;
import com.example.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<Movie> listPhimHot = movieService.getHotMovies(true, 1, 8).getContent();
        List<Movie> listPhimBo = movieService.getMovieByType(MovieType.PHIM_BO, true, 1, 6).getContent();
        List<Movie> listPhimLe = movieService.getMovieByType(MovieType.PHIM_LE, true, 1, 6).getContent();
        List<Movie> listPhimChieuRap = movieService.getMovieByType(MovieType.PHIM_CHIEU_RAP, true, 1, 6).getContent();
        List<Blog> listBlog = blogService.getBlogByType(true,1, 4).getContent();
        model.addAttribute("listPhimHot", listPhimHot);
        model.addAttribute("listPhimBo", listPhimBo);
        model.addAttribute("listPhimLe", listPhimLe);
        model.addAttribute("listPhimChieuRap", listPhimChieuRap);
        model.addAttribute("listBlog", listBlog);
        return "web/index";
    }
    @GetMapping("/phim-bo")
    public String getPhimBo(Model model,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "12") int pageSize) {
        Page<Movie> pageData = movieService.getMovieByType(MovieType.PHIM_BO, true, page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/phim-bo";
    }

    @GetMapping("/phim-le")
    public String getPhimLe(Model model,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "12") int pageSize
                            ) {
        Page<Movie> pageData = movieService.getMovieByType(MovieType.PHIM_LE, true, page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/phim-le";
    }

    @GetMapping("/phim-chieu-rap")
    public String getPhimChieuRap(Model model,
                                  @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "12") int pageSize
                                  ) {
        Page<Movie> pageData = movieService.getMovieByType(MovieType.PHIM_CHIEU_RAP, true, page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/phim-chieu-rap";
    }

    @GetMapping("/tin-tuc")
    public String getBlog(Model model,
                          @RequestParam(required = false, defaultValue = "1") int page,
                          @RequestParam(required = false, defaultValue = "12") int pageSize
                          ) {
        Page<Blog> pageData = blogService.getBlogByType(true, page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/danh-sach-blog";
    }

    @GetMapping("/phim/{id}/{slug}")
    public String getDetailMovie(Model model, @PathVariable("id") int id, @PathVariable("slug") String slug) {
        Movie movie = movieService.getMovie(id, slug, true);
        model.addAttribute("movie", movie);
        return "web/chi-tiet-phim";
    }

    @GetMapping("/tin-tuc/{id}/{slug}")
    public String getDetailBlog(Model model, @PathVariable("id") int id, @PathVariable("slug") String slug) {
        Blog blog = blogService.getBlog(id, slug, true);
        model.addAttribute("blog", blog);
        return "web/chi-tiet-blog";
    }

}