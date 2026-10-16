package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.domain.member.Role;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.request.PostMemberRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberLoanControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MemberRepository memberRepository;

    private MemberDto lendingMember;

    @BeforeEach
    void setUp() {
        lendingMember = RestAssured
                .given()
                .port(port)
                .when()
                .body(new PostMemberRequest(
                        "Glenn",
                        "The Member",
                        "glenn.member@example.com",
                        "Example Street",
                        "1",
                        8000,
                        "Exampletown",
                        "11-222-3333-4444",
                        Role.MEMBER
                ))
                .header()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);
    }

    @Test
    void test() {

    }
}