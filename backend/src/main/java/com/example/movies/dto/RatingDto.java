package com.example.movies.dto;

public class RatingDto {

    private String source;
    private String value;

    public RatingDto() {
    }

    public RatingDto(String source, String value) {
        this.source = source;
        this.value = value;
    }

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

