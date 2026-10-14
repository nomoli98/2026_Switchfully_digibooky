package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.service.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookServiceTest {
    private BookRepository bookRepository;
    private Book book;
    private Book book1;

    @BeforeEach
    void setUp() {
        Author author = new Author("Sammy", "Smart");
        book = new Book("isbn", "How to be awesome", author);
        book1 = new Book("otherIsbn", "How to be awesomer", author);
        bookRepository = new BookRepository();
        bookRepository.save(book);
        bookRepository.save(book1);
    }

    @Test
    @DisplayName("getting all books")
    void getAll_givenBooksExist_thenReturnBooks() {
        List<Book> resultBooks = bookRepository.getAll();
        Assertions.assertThat(resultBooks).hasSameElementsAs(List.of(book, book1));
    }

    @Test
    @DisplayName("getting one book by id when it exist")
    void getOne_givenBookExist_thenReturnBook() {
        Book resultBook = bookRepository.get(book.getId()).orElseThrow(() -> NotFoundException.bookNotFound(
                book.getId()));
        assertEquals(resultBook, book);
    }

    @Test
    @DisplayName("getting one book by id when it doesn't exist")
    void getOne_givenBookDoesntExist_thenThrowNotFoundException() {
        UUID newId = UUID.randomUUID();
        assertThrows(NotFoundException.class,
                () -> bookRepository.get(newId).orElseThrow(() -> NotFoundException.bookNotFound(newId)));
    }
}