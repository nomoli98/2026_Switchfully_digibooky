package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.domain.Member;
import com.application.digibookyproject.service.request.CreateMemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
    @LocalServerPort
    private int port;

    private record ErrorResponse(
            String timestamp,
            int status,
            String error,
            String message,
            String path
    ) {}

    private ValidatableResponse postUser(CreateMemberRequest request) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .port(port)
                .when()
                .post("/users")
                .then();
    }

    @Test
    @DisplayName("Adding a member succesfully returns the correct response")
    void postUser_givenRepositoryIsEmpty_thenReturnCreatedObject() {
        Member member = postUser(new CreateMemberRequest(
                        "Ron", "Reader", "ronreader@reading.com",
                        "Read Street", "4A",
                        8000, "Readsville", "12-345-6789"
                ))
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Member.class);

        Assertions.assertThat(member.getFirstname()).isEqualTo("Ron");
    }

    @Test
    @DisplayName("Adding a member succesfully returns the correct response")
    void postUser_givenEmailAlreadyExists_thenThrowsException() {
        postUser(new CreateMemberRequest(
                "Ron", "Reader", "ronreader@reading.com",
                "Read Street", "4A",
                8000, "Readsville", "12-345-6789"
        ));

        ErrorResponse response = postUser(new CreateMemberRequest(
                "Ron", "Reader", "ronreader@reading.com",
                "Read Street", "4A",
                8000, "Readsville", "12-345-6779"
        ))
                .assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .extract().as(ErrorResponse.class);

        Assertions.assertThat(response.message).isEqualTo("A user with email ronreader@reading.com already exists.");
    }

    @Test
    @DisplayName("Adding a member succesfully returns the correct response")
    void postUser_givenEmailEmpty_thenThrowsException() {
        ErrorResponse response = postUser(new CreateMemberRequest(
                "Ron", "Reader", null,
                "Read Street", "4A",
                8000, "Readsville", "12-345-6779"
        ))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        Assertions.assertThat(response.message).isEqualTo("Member parameter email is required");
    }


}