package com.application.digibookyproject.service;

import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.request.PostBookRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostBookServiceTest {
    BookRepository repository;
    PostBookRequest request;
    BookService service;
    LoanRepository loanRepository;

    @BeforeEach
    void initRepository() {
        repository = new BookRepository();
        loanRepository = new LoanRepository();
        service = new BookService(repository, loanRepository);
    }

    @Test
    @DisplayName("Create Book with a correct request")
    void givenValidRequest_whenCallingCreateBook_thenABookIsReturned() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThat(service.createBook(request).getIsbn()).isEqualTo("978-1-1234-5678-9");
    }

    @Test
    @DisplayName("Create Book with a correct isbn with spaces")
    void givenValidRequestWithSpaces_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978 1 1234 5678 9",
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with a correct isbn without -")
    void givenValidRequestWithOnlyInt_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "9781123456789",
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with an empty isbn in the request")
    void givenEmptyIsbnInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "",
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with a null isbn in the request")
    void givenNullIsbnInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                null,
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with an invalid isbn pattern in the request")
    void givenInvalidIsbnPatternInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-12-1234-5678-9",
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with an invalid isbn number in the request")
    void givenInvalidIsbnNumberInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "975-1-1234-5678-9",
                "le title",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with an empty title in the request")
    void givenEmptyTitleInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                "",
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with a null title in the request")
    void givenNullTitleInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                null,
                "le author firstname",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with an empty author first name in the request")
    void givenEmptyAuthorFirstNameInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                "",
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with a null author first name in the request")
    void givenNullAuthorFirstNameInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                null,
                "le author last name"
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with an empty author last name in the request")
    void givenEmptyAuthorLastNameInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                "le author firstname",
                ""
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Create Book with a null author last name in the request")
    void givenNullAuthorLastNameInRequest_whenCallingCreateBook_thenAnExceptionIsThrown() {
        request = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                "le author firstname",
                null
        );
        Assertions.assertThatThrownBy(() -> service.createBook(request)).isInstanceOf(BadRequestException.class);
    }
}