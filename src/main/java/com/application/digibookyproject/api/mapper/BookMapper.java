package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.AuthorDto;
import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.api.dto.LoanDto;
import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookMapper {
    public BookDto mapToDto(Book book) {
        Author author = book.getAuthor();
        LoanDto loanDto = Optional.ofNullable(book.getLoan())
                .map(Loan::getMember)
                .map(Member::getEmail)
                .map(LoanDto::new)
                .orElse(null);

        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                new AuthorDto(
                        author.getFirstname(),
                        author.getLastname()
                ),
                loanDto
        );
    }
}
