package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookRepositoryTest {
    @Test
    void get_givenBookIsStored_thenReturnBook() {
        BookRepository bookRepository = new BookRepository();
        Author author = new Author("Sammy", "Smart");
        Book book = new Book("isbn", "How to be awesome", author);

        bookRepository.saveWithId(book.getId(), book);

        Book resultBook = bookRepository.get(book.getId()).orElseThrow();
        assertEquals(book, resultBook);
        assertEquals(book.getTitle(), resultBook.getTitle());
        assertEquals(author.getFirstname(), resultBook.getAuthor().getFirstname());
        assertEquals(author.getLastname(), resultBook.getAuthor().getLastname());
    }
}