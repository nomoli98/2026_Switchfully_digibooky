package com.application.digibookyproject.service.predicate;

import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;

public class BookPredicate {
    private final GetBooksWithFilterRequest dto;

    public BookPredicate(GetBooksWithFilterRequest dto) {
        this.dto = dto;
    }

    private boolean isContained(String title, String toFind) {
        return title.toLowerCase().contains(toFind.toLowerCase());
    }

    public boolean applyFilters(Book book) {
        if (dto.title() != null && !isContained(book.getTitle(), dto.title())) return false;
        if (dto.isbn() != null && !book.getIsbn().equals(dto.isbn())) return false;
        if (dto.firstName() != null && !isContained(
                book.getAuthor().firstname(),
                dto.firstName()
        )) return false;
        if (dto.lastName() != null && !isContained(book.getAuthor().lastname(), dto.lastName()))
            return false;
        return true;
    }
}
