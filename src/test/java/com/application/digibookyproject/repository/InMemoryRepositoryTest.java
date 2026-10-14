package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.factory.MemberTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryRepositoryTest {
    @Test
    @DisplayName("getting a book that exists returns it")
    void get_givenBookIsStored_thenReturnBook() {
        InMemoryRepository<Book> bookRepository = new InMemoryRepository<>();
        Author author = new Author("Sammy", "Smart");
        Book book = new Book("isbn", "How to be awesome", author);

        bookRepository.saveWithId(book.getId(), book);

        Book resultBook = bookRepository.get(book.getId()).orElseThrow();
        assertEquals(book, resultBook);
        assertEquals(book.getTitle(), resultBook.getTitle());
        assertEquals(author.getFirstname(), resultBook.getAuthor().getFirstname());
        assertEquals(author.getLastname(), resultBook.getAuthor().getLastname());
    }
    
    @Test
    @DisplayName("getting a book that doesn't exist returns null")
    void get_givenBookDoesNotExist_thenReturnsNull() {
        InMemoryRepository<Book> bookRepository = new InMemoryRepository<>();

        assertFalse(bookRepository.get(UUID.randomUUID()).isPresent());
    }
    
    @Test
    @DisplayName("getting all books returns a list")
    void getAll_givenBooksExists_thenReturnsBooks() {
        InMemoryRepository<Book> bookRepository = new InMemoryRepository<>();
        Author author = new Author("Sammy", "Smart");
        Book book = new Book("isbn", "How to be awesome", author);
        Book book1 = new Book("otherIsbn", "How to be awesomer", author);

        bookRepository.saveWithId(book.getId(), book);
        bookRepository.saveWithId(book1.getId(), book1);

        List<Book> resultBooks = bookRepository.getAll();
        assertThat(List.of(book, book1)).hasSameElementsAs(resultBooks);
    }

    @Test
    @DisplayName("getting an optional when predicate is valid returns a member")
    void getOptional_whenPredicateIsValid_thenReturnMember() {
        InMemoryRepository<Member> repository = MemberTestData.createRepository(List.of());

        assertThat(repository.getOptional(member -> member.getLastname().equals("Willems")).isPresent()).isTrue();
    }

    @Test
    @DisplayName("getting an optional when predicate is invalid returns a member")
    void getOptional_whenPredicateIsInvalid_thenReturnMember() {
        InMemoryRepository<Member> repository = MemberTestData.createRepository(List.of());

        assertThat(repository.getOptional(member -> member.getLastname().equals("Jan")).isPresent()).isFalse();
    }
}