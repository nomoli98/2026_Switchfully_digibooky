package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.AuthorDto;
import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDto mapToDto(Book book) {
        Author author = book.getAuthor();

        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                new AuthorDto(
                        author.getFirstname(),
                        author.getLastname()
                )
        );
    }
}
