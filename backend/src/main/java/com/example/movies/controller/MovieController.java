package com.example.movies.controller;

import com.example.movies.dto.MovieDetailDto;
import com.example.movies.dto.MovieSearchResponseDto;
import com.example.movies.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public ResponseEntity<MovieSearchResponseDto> searchMovies(@RequestParam("query") String query) {
        MovieSearchResponseDto response = movieService.searchMovies(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDto> getMovieDetail(@PathVariable("id") String id) {
        MovieDetailDto detail = movieService.getMovieDetail(id);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
}

