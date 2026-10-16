package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.AuthorDto;
import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.api.dto.LoanInfoDto;
import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookMapper {
    public BookDto mapToDto(Book book) {
        Author author = book.getAuthor();
        LoanInfoDto loanInfoDto = Optional.ofNullable(book.getLoan())
                .map(loan -> new LoanInfoDto(
                        loan.getMember().getId(),
                        loan.getId(),
                        loan.getBook().getIsbn(),
                        loan.getDueDate()
                ))
                .orElse(null);

        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                new AuthorDto(
                        author.getFirstname(),
                        author.getLastname()
                ),
                loanInfoDto
        );
    }
}
