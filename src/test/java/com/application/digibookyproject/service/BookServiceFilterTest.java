package com.application.digibookyproject.service;

import com.application.digibookyproject.service.request.BookFilterQueryRequest;
import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookServiceFilterTest {
    Book book1, book2, book3, book4;
    BookRepository repository;
    BookService service;

    @BeforeEach
    void initRepositry() {
        repository = new BookRepository();

        book1 = new Book("le isbn", "le title", new Author("Le", "author"));
        book2 = new Book("la isbn", "la title", new Author("La", "auth0r"));
        book3 = new Book("le isbn", "la title because franchement voila", new Author("Le", "auth0r"));
        book4 = new Book("la isbn", "le title because franchement voila", new Author("La", "author"));

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        repository.save(book4);

        service = new BookService(repository);
    }

    @Test
    void givenIsbnInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(new BookFilterQueryRequest("le isbn", null, null, null));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book1, book3));
    }

    @Test
    void givenTitleInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(new BookFilterQueryRequest(null, "la title", null, null));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book3));
    }

    @Test
    void givenIsbnAndTitleInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(new BookFilterQueryRequest("la isbn", "la title", null, null));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2));
    }

    @Test
    void givenNullInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(new BookFilterQueryRequest(null, null, null, null));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book1, book2, book3, book4));
    }

    @Test
    void givenFirstNameInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(new BookFilterQueryRequest(null, null, "la", null));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book4));
    }

    @Test
    void givenLastNameInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(new BookFilterQueryRequest(null, null, null, "AUTH0r"));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book3));
    }

}