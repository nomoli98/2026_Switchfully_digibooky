package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.domain.member.Role;
import com.application.digibookyproject.factory.MemberTestData;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.ConflictException;
import com.application.digibookyproject.service.request.PostMemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MemberRepository memberRepository = MemberTestData.createRepository(List.of());
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("Calling createMember with correct arguments returns the created member")
    void createMember_givenValidArguments_thenCreateNewMember() {
        PostMemberRequest createMemberRequest = new PostMemberRequest(
                "Ron", "Reader", "ronreader@reading.com",
                "Read Street", "4A",
                8000, "Readsville", "12-345-6789", Role.MEMBER
        );

        Member result = memberService.createMember(createMemberRequest);
        assertEquals("Ron", result.getFirstname());
    }

    @Test
    @DisplayName("Calling createMember with email not matching regex throws BadRequestException")
    void createMember_givenInvalidEmail_thenThrowsException() {
        PostMemberRequest dto = new PostMemberRequest(
                "Ron", "Reader", "ronreader-reading.com",
                "Read Street", "4A",
                8000, "Readsville", "12-345-6789", Role.MEMBER
        );

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> memberService.createMember(dto)
        );
        assertEquals("Email ronreader-reading.com doesn't match pattern x@x.x", ex.getMessage());
    }

    public static Stream<Arguments> missingFieldSource() {
        return Stream.of(
                Arguments.of(
                        new PostMemberRequest(
                                "Ron", "Reader", null,
                                "Read Street", "4A",
                                8000, "Readsville", "12-345-6789", Role.MEMBER
                        ),
                        "email", "Member parameter email is required"
                ),
                Arguments.of(
                        new PostMemberRequest(
                                "Ron", "Reader", "ronreader@reading.com",
                                "Read Street", "4A",
                                8000, "Readsville", null, Role.MEMBER
                        ),
                        "inss", "Member parameter inss is required"
                ),
                Arguments.of(
                        new PostMemberRequest(
                                "", "Reader", "ronreader@reading.com",
                                "Read Street", "4A",
                                8000, "Readsville", "12-345-6789", Role.MEMBER
                        ),
                        "firstname", "Member parameter firstname is required"
                ),
                Arguments.of(
                        new PostMemberRequest(
                                "Ron", "Reader", "ronreader@reading.com",
                                "Read Street", "4A",
                                8000, "", "12-345-6789", Role.MEMBER
                        ),
                        "city", "Member parameter city is required"
                )
        );
    }

    @ParameterizedTest(name = "missing {1}")
    @MethodSource("missingFieldSource")
    @DisplayName("Missing fields on creation")
    void createMember_givenMissingField_thenThrowsBadRequestException(PostMemberRequest dto, String field, String exceptionMessage) {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> memberService.createMember(dto));
        assertEquals(exceptionMessage, ex.getMessage());
    }

    public static Stream<Arguments> duplicateFieldSource() {
        return Stream.of(
                Arguments.of(
                        new PostMemberRequest(
                                "Ron", "Reader", "jan.peeters@example.com",
                                "Read Street", "4A",
                                8000, "Readsville", "12-345-6789", Role.MEMBER
                        ),
                        "email", "A user with email jan.peeters@example.com already exists."
                ),
                Arguments.of(
                        new PostMemberRequest(
                                "Ron", "Reader", "ronreader@reading.com",
                                "Read Street", "4A",
                                8000, "Readsville", "90.07.22-234.56", Role.MEMBER
                        ),
                        "inss", "A user with inss number 90.07.22-234.56 already exists."
                )
        );
    }

    @ParameterizedTest(name = "duplicate {1}")
    @MethodSource("duplicateFieldSource")
    @DisplayName("Duplicate fields on creation")
    void createMember_givenDuplicate_thenThrowsConflictException(PostMemberRequest dto, String field, String exceptionMessage) {
        ConflictException ex = assertThrows(ConflictException.class, () -> memberService.createMember(dto));
        assertEquals(exceptionMessage, ex.getMessage());
    }
}