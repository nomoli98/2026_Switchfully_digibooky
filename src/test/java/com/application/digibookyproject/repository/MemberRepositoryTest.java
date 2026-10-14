package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.factory.MemberTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    private MemberRepository memberRepository;
    private final Member storedJulie = new Member("Martin", "Julie", "julie.martin@example.com", "Nieuwstraat", "18",
            2000, "Antwerpen", "99.03.14-890.12");

    @BeforeEach
    void setUp() {
        memberRepository = MemberTestData.createRepository(List.of(storedJulie));
    }

    @Test
    @DisplayName("getMemberWithInss returns a member when present")
    void getMemberWithInss_givenMemberExists_thenReturnExistingMember() {
        Optional<Member> julieOptional = memberRepository.getMemberWithInss("99.03.14-890.12");
        Member julie = julieOptional.orElseThrow();
        assertEquals(storedJulie, julie);
    }

    @Test
    @DisplayName("getMemberWithInss returns an empty optional when not present")
    void getMemberWithInss_givenMemberDoesNotExist_thenReturnEmptyOptional() {
        Optional<Member> julieOptional = memberRepository.getMemberWithInss("99.03.14-890.23");
        assertTrue(julieOptional.isEmpty());
    }

    @Test
    @DisplayName("getMemberWithEmail returns a member when present")
    void getMemberWithEmail_givenMemberExists_thenReturnExistingMember() {
        Optional<Member> julieOptional = memberRepository.getMemberWithEmail("julie.martin@example.com");
        Member julie = julieOptional.orElseThrow();
        assertEquals(storedJulie, julie);
    }

    @Test
    @DisplayName("getMemberWithEmail returns an empty optional when not present")
    void getMemberWithEmail_givenMemberDoesNotExist_thenReturnEmptyOptional() {
        Optional<Member> julieOptional = memberRepository.getMemberWithEmail("marco.polo@example.com");
        assertTrue(julieOptional.isEmpty());
    }
}