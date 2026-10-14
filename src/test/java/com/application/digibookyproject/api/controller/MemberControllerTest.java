package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.domain.member.Member;
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
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberControllerTest {
    @LocalServerPort
    private int port;

    private record ErrorResponse(
            String timestamp,
            int status,
            String error,
            String message,
            String path
    ) {
    }

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
                .statusCode(HttpStatus.CREATED.value())
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

        Assertions.assertThat(response.message).isEqualTo(
                "A user with email ronreader@reading.com already exists.");
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

    @Test
    @DisplayName("getting all users when user is unauthenticated returns unauthorized")
    void getAllUsers_givenUnauthenticated_returnsUnauthorized() {
        ErrorResponse response = RestAssured.given()
                .port(port)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract().as(ErrorResponse.class);

        Assertions.assertThat(response.message).isEqualTo("Invalid Credentials");
    }

    @Test
    @DisplayName("getting all users when user is unauthorized returns forbidden")
    void getAllUsers_givenUnauthorized_returnsForbidden() {
        postUser(new CreateMemberRequest(
                "Ron", "Reader", "ronreader@reading.com",
                "Read Street", "4A",
                8000, "Readsville", "12-345-6789"
        ));
        ErrorResponse response = RestAssured.given()
                .port(port)
                .header("Authorization", "ronreader@reading.com")
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract().as(ErrorResponse.class);

        Assertions.assertThat(response.message).isEqualTo(
                "You don't have permission to view members");
    }

    @Test
    @DisplayName("getting all users when user is authorized returns users")
    void getAllUsers_givenAuthorized_returnsUsers() {
        MemberDto[] response = RestAssured.given()
                .port(port)
                .header("Authorization", "admin@example.com")
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(MemberDto[].class);

        Assertions.assertThat(response.length).isEqualTo(1);
    }
}