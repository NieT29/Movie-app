package com.example.movieapp.controller;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public String getIndexPage(Model model) {
        List<Blog> blogs = blogService.getAllBlogs();
        model.addAttribute("blogs", blogs);
        return "admin/blog/index";
    }

    @GetMapping("/own-blogs")
    public String getOwnPage(Model model) {
        List<Blog> blogs = blogService.getOwnBlogs();
        model.addAttribute("blogs", blogs);
        return "admin/blog/own";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "admin/blog/create";
    }

    @GetMapping("/{id}")
    public String getDetailPage(Model model, @PathVariable Integer id) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "admin/blog/detail";
    }
}
