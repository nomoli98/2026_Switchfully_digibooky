package com.application.digibookyproject.service.filter;

import com.application.digibookyproject.service.request.BookFilterQueryRequest;
import com.application.digibookyproject.domain.Book;

public class BookFilter {
    private BookFilterQueryRequest dto;

    public BookFilter(BookFilterQueryRequest dto) {
        this.dto = dto;
    }

    private boolean isContained(String title, String toFind) {
        return title.toLowerCase().contains(toFind.toLowerCase());
    }

    public boolean applyFilters(Book book) {
        if (dto.title() != null && !isContained(book.getTitle(), dto.title())) return false;
        if (dto.isbn() != null && !book.getIsbn().equals(dto.isbn())) return false;
        if (dto.firstName() != null && !isContained(book.getAuthor().getFirstname(), dto.firstName())) return false;
        if (dto.lastName() != null && !isContained(book.getAuthor().getLastname(), dto.lastName())) return false;
        return true;
    }
}
