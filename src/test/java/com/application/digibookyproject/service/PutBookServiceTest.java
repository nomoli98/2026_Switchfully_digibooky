package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.service.exception.NotFoundException;
import com.application.digibookyproject.service.request.PostBookRequest;
import com.application.digibookyproject.service.request.PutBookRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class PutBookServiceTest {
    BookRepository repository;
    PutBookRequest request;
    BookService service;
    PostBookRequest postRequest;
    Book book;


    @BeforeEach
    void initRepository() {
        repository = new BookRepository();
        service = new BookService(repository, new LoanRepository());

        postRequest = new PostBookRequest(
                "978-1-1234-5678-9",
                "Convert",
                "Jon",
                "Doe"
        );

        book = service.createBook(postRequest);
    }

    @DisplayName("Put Book with a valid UUID to return the updated Book")
    @Test
    void givenCorrectRequest_whenCallingUpdateBook_thenUpdatedBookIsReturned() {
        request = new PutBookRequest(book.getId(), "Converge", "Jane", "Doe");
        Book updatedBook = service.updateBook(request);
        Assertions.assertThat(updatedBook.getTitle()).isNotEqualTo("Convert");
        Assertions.assertThat(updatedBook.getAuthor().getFirstname()).isNotEqualTo("Jon");
    }

    @DisplayName("Put Book with a random invalid UUID to throw an NotFoundException")
    @Test
    void givenIncorrectUuid_whenCallingUpdateBook_thenAnExceptionIsThrown() {
        request = new PutBookRequest(UUID.randomUUID(), "Converge", "Jane", "Doe");
        Assertions.assertThatThrownBy(() -> service.updateBook(request)).isInstanceOf(NotFoundException.class);
    }
}