package com.example.movies.dto;

import java.util.List;

public class MovieSearchResponseDto {

    private List<MovieSummaryDto> results;
    private int totalResults;

    public MovieSearchResponseDto() {
    }

    public MovieSearchResponseDto(List<MovieSummaryDto> results, int totalResults) {
        this.results = results;
        this.totalResults = totalResults;
    }

    public List<MovieSummaryDto> getResults() {
        return results;
    }

    public void setResults(List<MovieSummaryDto> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

