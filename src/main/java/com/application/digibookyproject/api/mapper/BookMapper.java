package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDto mapToDto(Book book) {
        return new BookDto(book.getId(), book.getIsbn(), book.getTitle());
    }
}
