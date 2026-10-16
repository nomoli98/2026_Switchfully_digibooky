package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.api.dto.CreateLoanDto;
import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.domain.member.Role;
import com.application.digibookyproject.service.request.PostBookRequest;
import com.application.digibookyproject.service.request.PostLoanRequest;
import com.application.digibookyproject.service.request.PostMemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberLoanControllerTest {
    @LocalServerPort
    private int port;

    private MemberDto lendingMember;
    private MemberDto librarian;
    private BookDto book;

    private RequestSpecification requestAs(String email) {
        return RestAssured
                .given()
                .port(port)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", email)
                .when();
    }

    private MemberDto createMember(PostMemberRequest request) {
        return requestAs("admin@example.com")
                .body(request)
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);
    }

    @BeforeEach
    void setUp() {
        lendingMember = createMember(new PostMemberRequest(
                "Glenn",
                "The Member",
                "glenn.member@example.com",
                "Example Street",
                "1",
                8000,
                "Exampletown",
                "11-222-3333-4444",
                Role.MEMBER
        ));
        librarian = createMember(new PostMemberRequest(
                "Lenn",
                "The Librarian",
                "lenn.librarian@example.com",
                "Example Street",
                "1",
                8000,
                "Exampletown",
                "11-333-3333-4444",
                Role.LIBRARIAN
        ));

        book = requestAs(librarian.email())
                .body(new PostBookRequest(
                        "978-111-111-11-11",
                        "Le title",
                        "Guillaume",
                        "Lastname"
                ))
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookDto.class);
    }

    @Nested
    @DisplayName("as a librarian")
    class AsALibrarian {
        @Test
        @DisplayName("given a member has one loan when getting all loans for said member then return loan")
        void givenMemberHasOneLoan_whenGettingAllLoans_thenReturnLoan() {
            CreateLoanDto loanDto = requestAs(lendingMember.email())
                    .body(new PostLoanRequest(lendingMember.email(), book.id()))
                    .post("/loans")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(CreateLoanDto.class);

            CreateLoanDto[] loans = requestAs(librarian.email())
                    .get("/users/%s/loans".formatted(lendingMember.id()))
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(CreateLoanDto[].class);

            Assertions.assertThat(loans.length).isEqualTo(1);
            Assertions.assertThat(loanDto).isEqualTo(loans[0]);
        }

        @Test
        @DisplayName("given a member has no loan when getting all loans for said member then return empty list")
        void givenMemberHasNoLoan_whenGettingAllLoans_thenReturnEmptyList() {
            CreateLoanDto[] loans = requestAs(librarian.email())
                    .get("/users/%s/loans".formatted(lendingMember.id()))
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(CreateLoanDto[].class);

            Assertions.assertThat(loans).isEmpty();
        }
    }

    @Nested
    @DisplayName("as an regular member")
    class AsARegularMember {
        @Test
        @DisplayName("getting all loans returns forbidden status code")
        void whenGettingAllLoans_thenReturnForbidden() {
            requestAs(lendingMember.email())
                    .get("/users/%s/loans".formatted(lendingMember.id()))
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value());
        }
    }
}