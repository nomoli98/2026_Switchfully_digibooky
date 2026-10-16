package com.application.digibookyproject.service;

import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.request.PostBookRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    public static Stream<Arguments> badIsbnStream() {
        return Stream.of(
                        "978 1 1234 5678 9",
                        "9781123456789",
                        "",
                        null,
                        "978-12-1234-5678-9",
                        "975-1-1234-5678-9"

                )
                .map(Arguments::of);
    }

    @ParameterizedTest(name = "isbn {0}")
    @MethodSource("badIsbnStream")
    @DisplayName("Bad isbn numbers")
    void givenBadIsbn_whenCreateBook_thenThrowException(String isbn) {
        Assertions.assertThatThrownBy(
                () -> service.createBook(new PostBookRequest(
                        isbn,
                        "title",
                        "first",
                        "last"
                ))
        ).isInstanceOf(BadRequestException.class);
    }

    public static Stream<Arguments> badRequestStream() {
        return Stream.of(
                Arguments.of("", "le author firstname", "le author last name"),
                Arguments.of(null, "le author firstname", "le author last name"),
                Arguments.of("le title", "", "le author last name"),
                Arguments.of("le title", null, "le author last name"),
                Arguments.of("le title", "le author firstname", ""),
                Arguments.of("le title", "le author firstname", null)
        );
    }

    @ParameterizedTest(name = "title: {0} | author: {1} {2}")
    @DisplayName("Create Book with an empty author last name in the request")
    @MethodSource("badRequestStream")
    void givenBadRequest_whenCallingCreateBook_thenAnExceptionIsThrown(
            String title,
            String firstName,
            String lastName
    ) {
        Assertions.assertThatThrownBy(() -> service.createBook(new PostBookRequest(
                "978-1-1234-5678-9",
                title,
                firstName,
                lastName
        ))).isInstanceOf(BadRequestException.class);
    }
}