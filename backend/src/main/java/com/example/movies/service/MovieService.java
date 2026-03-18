package com.example.movies.service;

import com.example.movies.dto.MovieDetailDto;
import com.example.movies.dto.MovieSearchResponseDto;
import com.example.movies.dto.MovieSummaryDto;
import com.example.movies.dto.RatingDto;
import com.example.movies.service.OmdbClient.OmdbMovieDetail;
import com.example.movies.service.OmdbClient.OmdbMovieSummary;
import com.example.movies.service.OmdbClient.OmdbRating;
import com.example.movies.service.OmdbClient.OmdbSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final OmdbClient omdbClient;

    public MovieService(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public MovieSearchResponseDto searchMovies(String query) {
        if (!StringUtils.hasText(query)) {
            throw new IllegalArgumentException("El parámetro de búsqueda no puede estar vacío.");
        }

        OmdbSearchResponse omdbResponse = omdbClient.searchMovies(query.trim());
        if (omdbResponse == null || !"True".equalsIgnoreCase(omdbResponse.getResponse())) {
            // Devuelve 0 resultados de forma controlada
            return new MovieSearchResponseDto(Collections.emptyList(), 0);
        }

        List<MovieSummaryDto> results = omdbResponse.getSearch().stream()
                .filter(Objects::nonNull)
                .map(this::mapToSummaryDto)
                .collect(Collectors.toList());

        int total = 0;
        if (StringUtils.hasText(omdbResponse.getTotalResults())) {
            try {
                total = Integer.parseInt(omdbResponse.getTotalResults());
            } catch (NumberFormatException ignored) {
            }
        }

        return new MovieSearchResponseDto(results, total);
    }

    public MovieDetailDto getMovieDetail(String imdbId) {
        if (!StringUtils.hasText(imdbId)) {
            throw new IllegalArgumentException("El id de la película no puede estar vacío.");
        }

        OmdbMovieDetail detail = omdbClient.getMovieDetail(imdbId.trim());
        if (detail == null || !"True".equalsIgnoreCase(detail.getResponse())) {
            throw new RuntimeException("No se encontró la película solicitada.");
        }

        return mapToDetailDto(detail);
    }

    private MovieSummaryDto mapToSummaryDto(OmdbMovieSummary source) {
        String poster = (source.getPoster() == null || "N/A".equalsIgnoreCase(source.getPoster()))
                ? null
                : source.getPoster();
        return new MovieSummaryDto(
                source.getImdbId(),
                source.getTitle(),
                source.getYear(),
                source.getType(),
                poster
        );
    }

    private MovieDetailDto mapToDetailDto(OmdbMovieDetail source) {
        MovieDetailDto dto = new MovieDetailDto();
        dto.setId(source.getImdbId());
        dto.setTitle(source.getTitle());
        dto.setYear(source.getYear());
        dto.setGenre(source.getGenre());
        dto.setDirector(source.getDirector());
        dto.setActors(source.getActors());
        dto.setPlot(source.getPlot());
        dto.setRuntime(source.getRuntime());
        dto.setLanguage(source.getLanguage());
        dto.setCountry(source.getCountry());
        dto.setImdbRating(source.getImdbRating());

        String poster = (source.getPoster() == null || "N/A".equalsIgnoreCase(source.getPoster()))
                ? null
                : source.getPoster();
        dto.setPosterUrl(poster);

        List<RatingDto> ratings = source.getRatings() == null ? Collections.emptyList()
                : source.getRatings().stream()
                .filter(Objects::nonNull)
                .map(this::mapToRatingDto)
                .collect(Collectors.toList());
        dto.setRatings(ratings);

        return dto;
    }

    private RatingDto mapToRatingDto(OmdbRating rating) {
        return new RatingDto(rating.getSource(), rating.getValue());
    }
}

