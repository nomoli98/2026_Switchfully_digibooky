package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeleteBookServiceTest {

    BookRepository repository;
    Book book;
    BookService service;
    UUID id;


    @BeforeEach
    void initRepository() {
        repository = new BookRepository();
        service = new BookService(repository);
        book = new Book(
                "978-1-1234-5678-9",
                "le title",
                new Author(
                        "le author firstname",
                        "le author last name"
                )
        );
        repository.save(book);
        id = repository.getAll().getFirst().getId();
    }

    @Test
    @DisplayName("Deleting an existing book return the deleted book")
    void givenExistingBookToDelete_whenCallingDeletingBook_thenReturnsTheDeletedBook() {
        assertEquals(book.getIsbn(), service.deleteBook(id).getIsbn());
    }

    @Test
    @DisplayName("Deleting an invalid book throws an exception")
    void givenNotExistingBookToDelete_whenCallingDeletingBook_thenThrowsException() {
        Assertions.assertThatThrownBy(() -> service.deleteBook(UUID.randomUUID())).isInstanceOf(Exception.class);
    }

}