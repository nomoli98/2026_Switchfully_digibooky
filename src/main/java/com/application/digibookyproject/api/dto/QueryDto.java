package com.application.digibookyproject.api.dto;

public class QueryDto {
    private final String isbn;
    private final String title;

    public QueryDto(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }
}
