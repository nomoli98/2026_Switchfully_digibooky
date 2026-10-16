package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.api.dto.LoanDto;
import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.domain.member.Role;
import com.application.digibookyproject.service.request.PostBookRequest;
import com.application.digibookyproject.service.request.PostLoanRequest;
import com.application.digibookyproject.service.request.PostMemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTest {
    @LocalServerPort
    private int port;

    private MemberDto lendingMember;
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
        MemberDto librarian = createMember(new PostMemberRequest(
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

    @Test
    @DisplayName("getBook returns the email of the member who lent it if there is one")
    void getBook_givenBookHasLoan_thenReturnsLenderEmail() {
        LoanDto loanDto = requestAs(lendingMember.email())
                .body(new PostLoanRequest(lendingMember.email(), book.id()))
                .post("/loans")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(LoanDto.class);

        BookDto bookDto = requestAs("")
                .get("books/" + book.id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);

        assertEquals(lendingMember.id(), bookDto.loanInfoDto().memberId());
        assertEquals(loanDto.id(), bookDto.loanInfoDto().loanId());
        assertEquals(bookDto.isbn(), bookDto.loanInfoDto().isbn());
        assertEquals(loanDto.dueDate(), bookDto.loanInfoDto().dueDate());
    }

    @Test
    @DisplayName("getBook returns no email if there is no loan")
    void getBook_givenBookHasNoLoan_thenReturnsNoLenderEmail() {
        BookDto bookDto = requestAs("")
                .get("books/" + book.id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);

        assertNull(bookDto.loanInfoDto());
    }
}