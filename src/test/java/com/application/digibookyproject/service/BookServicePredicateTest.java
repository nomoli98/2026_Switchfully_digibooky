package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookServicePredicateTest {
    Book book1, book2, book3, book4;
    BookRepository repository;
    BookService service;
    LoanRepository loanRepository;

    @BeforeEach
    void initRepository() {
        repository = new BookRepository();
        loanRepository = new LoanRepository();

        book1 = new Book("le isbn", "le title", new Author("Le", "author"));
        book2 = new Book("la isbn", "la title", new Author("La", "auth0r"));
        book3 = new Book("le isbn", "la title because honestly voila", new Author("Le", "auth0r"));
        book4 = new Book("la isbn", "le title because honestly voila", new Author("La", "author"));

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        repository.save(book4);
    }

    @Test
    @DisplayName("Calling getAllBooks with a isbn filter returns the correct result")
    void givenIsbnInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(new GetBooksWithFilterRequest(
                "le isbn",
                null,
                null,
                null
        ));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book1, book3));
    }

    @Test
    @DisplayName("Calling getAllBooks with a title filter returns the correct result")
    void givenTitleInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(new GetBooksWithFilterRequest(
                null,
                "la title",
                null,
                null
        ));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book3));
    }

    @Test
    @DisplayName("Calling getAllBooks with a title and isbn filters returns the correct result")
    void givenIsbnAndTitleInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(new GetBooksWithFilterRequest(
                "la isbn",
                "la title",
                null,
                null
        ));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2));
    }

    @Test
    @DisplayName("Calling getAllBooks without filters returns the correct result")
    void givenNullInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(new GetBooksWithFilterRequest(
                null,
                null,
                null,
                null
        ));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(
                book1,
                book2,
                book3,
                book4
        ));
    }

    @Test
    @DisplayName("Calling getAllBooks with a firstname filter returns the correct result")
    void givenFirstNameInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(new GetBooksWithFilterRequest(
                null,
                null,
                "la",
                null
        ));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book4));
    }

    @Test
    @DisplayName("Calling getAllBooks with a lastname filter returns the correct result")
    void givenLastNameInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(new GetBooksWithFilterRequest(
                null,
                null,
                null,
                "AUTH0r"
        ));
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book3));
    }
}