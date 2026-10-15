package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.service.BookService;
import com.application.digibookyproject.service.MemberService;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;
import com.application.digibookyproject.service.request.PostBookRequest;
import com.application.digibookyproject.service.request.PostLoanRequest;
import com.application.digibookyproject.service.request.PostMemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private BookService bookService;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("a valid loan request saves a loan then returns it")
    void givenValidLoanRequest_whenRequested_thenReturnsLoan() {
        //Given
        PostBookRequest bookRequest = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                "le author firstname",
                "le author last name");
        bookService.createBook(bookRequest);
        PostMemberRequest memberRequest = new PostMemberRequest(
                "Ron",
                "Reader",
                "ronreader@reading.com",
                "Read Street",
                "4A",
                8000,
                "Readsville",
                "12-345-6789");
        memberService.createMember(memberRequest);
        Book book = bookService.getAllBooks(new GetBooksWithFilterRequest(null, null, null, null)).getFirst();
        Member member = memberService.getAllMembers().getFirst();
        PostLoanRequest request = new PostLoanRequest(member.getEmail(), book.getId());

        //When
        Loan loan = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .port(port)
                .when()
                .post("/loans")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(Loan.class);

        //Then
        Assertions.assertThat(loan.getMemberId()).isEqualTo(member.getId());
        Assertions.assertThat(loan.getBookId()).isEqualTo(book.getId());
        Assertions.assertThat(loan.getDueDate())
                .isEqualTo(LocalDate.now().plusWeeks(Loan.DEFAULT_LOAN_DURATION_IN_WEEKS));
    }
}