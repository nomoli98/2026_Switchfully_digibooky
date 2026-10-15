package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.domain.member.Role;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.exception.ForbiddenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SecurityServiceTest {

    public static Stream<Arguments> adminPermissions() {
        return Stream.of(
                Permission.REGISTER_ADMIN,
                Permission.REGISTER_LIBRARIAN,
                Permission.VIEW_MEMBERS
        ).map(Arguments::of);
    }

    @ParameterizedTest(name = "Admin user has permission {0}")
    @MethodSource("adminPermissions")
    @DisplayName("Admin permissions")
    void givenAdminUser_whenHasPermission_thenAllow(Permission permission) {
        MemberRepository memberRepository = new MemberRepository();
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertDoesNotThrow(() -> securityService.validateAuthorization(
                "admin@example.com",
                permission
        ));
    }

    @Test
    @DisplayName("Admin user does not have permission register book")
    void givenAdminUser_whenAddBook_thenDecline() {
        MemberRepository memberRepository = new MemberRepository();
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertThrows(
                ForbiddenException.class, () -> securityService.validateAuthorization(
                        "admin@example.com",
                        Permission.REGISTER_BOOK
                )
        );
    }

    @Test
    @DisplayName("Librarian user does have permission register book")
    void givenLibrarianUser_whenRegisterBook_thenAllow() {
        Member librarian = new Member(
                "librarian",
                "librarian",
                "librarian@example.com",
                null,
                null,
                0,
                null,
                null
        );
        librarian.setRole(Role.LIBRARIAN);
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.save(librarian);
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertDoesNotThrow(() -> securityService.validateAuthorization(
                "librarian@example.com",
                Permission.REGISTER_BOOK
        ));
    }

    @ParameterizedTest(name = "Librarian user doesn't have permission {0}")
    @MethodSource("adminPermissions")
    @DisplayName("Librarian user does not have permission from admin")
    void givenLibrarianUser_whenAdminAction_thenDecline(Permission permission) {
        Member librarian = new Member(
                "librarian",
                "librarian",
                "librarian@example.com",
                null,
                null,
                0,
                null,
                null
        );
        librarian.setRole(Role.LIBRARIAN);
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.save(librarian);
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertThrows(
                ForbiddenException.class, () -> securityService.validateAuthorization(
                        "librarian@example.com",
                        permission
                )
        );
    }
}