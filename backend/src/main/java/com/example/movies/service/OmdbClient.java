package com.example.movies.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OmdbClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    public OmdbClient(RestTemplate restTemplate,
                      @Value("${omdb.api.key}") String apiKey,
                      @Value("${omdb.api.base-url:https://www.omdbapi.com/}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public OmdbSearchResponse searchMovies(String query) {
        String url = baseUrl + "?apikey=" + apiKey + "&type=movie&s={query}";
        try {
            ResponseEntity<OmdbSearchResponse> response =
                    restTemplate.getForEntity(url, OmdbSearchResponse.class, query);
            return response.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Error al conectar con OMDb", ex);
        }
    }

    public OmdbMovieDetail getMovieDetail(String imdbId) {
        String url = baseUrl + "?apikey=" + apiKey + "&i={id}&plot=full";
        try {
            ResponseEntity<OmdbMovieDetail> response =
                    restTemplate.getForEntity(url, OmdbMovieDetail.class, imdbId);
            return response.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Error al conectar con OMDb", ex);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OmdbSearchResponse {

        @JsonAlias("Search")
        private List<OmdbMovieSummary> search;

        @JsonAlias("totalResults")
        private String totalResults;

        @JsonAlias("Response")
        private String response;

        @JsonAlias("Error")
        private String error;

        public List<OmdbMovieSummary> getSearch() {
            return search;
        }

        public void setSearch(List<OmdbMovieSummary> search) {
            this.search = search;
        }

        public String getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(String totalResults) {
            this.totalResults = totalResults;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OmdbMovieSummary {

        @JsonAlias("imdbID")
        private String imdbId;

        @JsonAlias("Title")
        private String title;

        @JsonAlias("Year")
        private String year;

        @JsonAlias("Type")
        private String type;

        @JsonAlias("Poster")
        private String poster;

        public String getImdbId() {
            return imdbId;
        }

        public void setImdbId(String imdbId) {
            this.imdbId = imdbId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OmdbMovieDetail {

        @JsonAlias("imdbID")
        private String imdbId;

        @JsonAlias("Title")
        private String title;

        @JsonAlias("Year")
        private String year;

        @JsonAlias("Genre")
        private String genre;

        @JsonAlias("Director")
        private String director;

        @JsonAlias("Actors")
        private String actors;

        @JsonAlias("Plot")
        private String plot;

        @JsonAlias("Runtime")
        private String runtime;

        @JsonAlias("Language")
        private String language;

        @JsonAlias("Country")
        private String country;

        @JsonAlias("imdbRating")
        private String imdbRating;

        @JsonAlias("Poster")
        private String poster;

        @JsonAlias("Ratings")
        private List<OmdbRating> ratings;

        @JsonAlias("Response")
        private String response;

        @JsonAlias("Error")
        private String error;

        public String getImdbId() {
            return imdbId;
        }

        public void setImdbId(String imdbId) {
            this.imdbId = imdbId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActors() {
            return actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getPlot() {
            return plot;
        }

        public void setPlot(String plot) {
            this.plot = plot;
        }

        public String getRuntime() {
            return runtime;
        }

        public void setRuntime(String runtime) {
            this.runtime = runtime;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getImdbRating() {
            return imdbRating;
        }

        public void setImdbRating(String imdbRating) {
            this.imdbRating = imdbRating;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public List<OmdbRating> getRatings() {
            return ratings;
        }

        public void setRatings(List<OmdbRating> ratings) {
            this.ratings = ratings;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OmdbRating {

        @JsonAlias("Source")
        private String source;

        @JsonAlias("Value")
        private String value;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

