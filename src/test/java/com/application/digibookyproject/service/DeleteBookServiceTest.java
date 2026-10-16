package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.service.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteBookServiceTest {
    BookRepository repository;
    Book book;
    BookService service;
    LoanRepository loanRepository;

    @BeforeEach
    void initRepository() {
        repository = new BookRepository();
        loanRepository = new LoanRepository();
        service = new BookService(repository, loanRepository);
        book = new Book(
                "978-1-1234-5678-9",
                "le title",
                new Author(
                        "le author firstname",
                        "le author last name"
                )
        );
        repository.save(book);
    }

    @Test
    @DisplayName("Deleting an existing book return the deleted book")
    void givenExistingBookToDelete_whenCallingDeletingBook_thenReturnsTheDeletedBook() {
        assertEquals(book.getIsbn(), service.deleteBook(book.getId()).getIsbn());
    }

    @Test
    @DisplayName("Deleting an invalid book throws an exception")
    void givenNotExistingBookToDelete_whenCallingDeletingBook_thenThrowsException() {
        Assertions.assertThatThrownBy(
                        () -> service.deleteBook(UUID.randomUUID()))
                .isInstanceOf(NotFoundException.class
                );
    }
}