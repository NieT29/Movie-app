package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.User;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.FavoriteRequest;
import com.example.movieapp.repository.FavoriteRepository;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.repository.UserRepository;
import com.example.movieapp.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

    @Override
    public List<Favorite> findByUser_IdOrderByCreatedAtDesc() {
        User user = (User) session.getAttribute("currentUser");
        return favoriteRepository.findByUser_IdOrderByCreatedAtDesc(user.getId());
    }


    @Override
    public Favorite findFavoriteForCurrentUserAndMovie(Integer movieId) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            return favoriteRepository.findByUser_IdAndMovie_Id(currentUser.getId(), movieId);
        }
        return null;
    }


    @Override
    public Favorite addToFavorite(FavoriteRequest request) {
        User user = (User) session.getAttribute("currentUser");

        //kiểm tra xem phim có tồn tại không
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // thêm vào phim yêu thích
        Favorite favorite = Favorite.builder()
                .createdAt(LocalDateTime.now())
                .movie(movie)
                .user(user)
                .build();
        favoriteRepository.save(favorite);
        return favorite;
    }

    @Override
    public void removeFromFavorite(Integer id) {
        User user = (User) session.getAttribute("currentUser");

        // kiểm tra xem phim có tồn tại trong phim yêu thích hay không
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found"));

        // kiểm tra xem người dùng đang thao tác có phải là chủ sở hữu của phim yêu thích không
        if (!favorite.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User not authorized to update favorite");
        }

        favoriteRepository.delete(favorite);
    }
}
