package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    @LocalServerPort
    private int port;
    
    @Test
    @DisplayName("getting all books on an empty repo returns an empty list")
    void getAll_givenRepositoryIsEmpty_thenReturnEmptyList() {
        BookDto[] emptyList = RestAssured
                .given()
                .port(port)
                .when()
                .get("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        Assertions.assertThat(emptyList).isEmpty();
    }
}