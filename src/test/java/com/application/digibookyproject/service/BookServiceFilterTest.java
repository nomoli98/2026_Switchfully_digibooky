package com.application.digibookyproject.service;

import com.application.digibookyproject.api.dto.QueryDto;
import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookServiceFilterTest {
    QueryDto dto1, dto2, dto3, dto4;
    Book book1, book2, book3, book4;
    BookRepository repository;
    BookService service;

    @BeforeEach
    void initRepositry() {
        repository = new BookRepository();

        book1 = new Book("le isbn", "le title", new Author("Le", "author"));
        book2 = new Book("la isbn", "la title", new Author("La", "author"));
        book3 = new Book("le isbn", "la title because franchement voila", new Author("Le", "author"));
        book4 = new Book("la isbn", "le title because franchement voila", new Author("La", "author"));

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        repository.save(book4);

        service = new BookService(repository);

        dto1 = new QueryDto("le isbn", null);
        dto2 = new QueryDto("la isbn", "le title");
        dto3 = new QueryDto(null, "la title");
        dto4 = new QueryDto(null, null);
    }

    @Test
    void givenIsbnInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(dto1);
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book1, book3));
    }

    @Test
    void givenTitleInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(dto3);
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book2, book3));
    }

    @Test
    void givenIsbnAndTitleInQueryDto_whenCallingGetAllBooks_thenFilteredListIsReturned() {
        List<Book> books = service.getAllBooks(dto2);
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book4));
    }

    @Test
    void givenNullInQueryDto_whenCallingGetAllBooks_thenAllBooksIsReturned() {
        List<Book> books = service.getAllBooks(dto4);
        Assertions.assertThat(books).containsExactlyInAnyOrderElementsOf(List.of(book1, book2, book3, book4));
    }

}