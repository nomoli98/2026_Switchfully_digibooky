package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.exception.ForbiddenException;
import com.application.digibookyproject.service.exception.UnauthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SecurityServiceTest {
    @Test
    @DisplayName("validateAuthorization does not throw exception when authorized")
    void givenAdminUser_whenHasPermission_thenAllow() {
        MemberRepository memberRepository = new MemberRepository();
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertDoesNotThrow(() -> securityService.validateAuthorization(
                "admin@example.com",
                Permission.REGISTER_LIBRARIAN
        ));
    }

    @Test
    @DisplayName("validateAuthorization throws exception when not authorized")
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
    @DisplayName("validateAuthorization throws exception when not authenticated")
    void givenNullUser_whenAuthorize_thenDecline() {
        MemberRepository memberRepository = new MemberRepository();
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertThrows(
                UnauthorizedException.class, () -> securityService.validateAuthorization(
                        null,
                        Permission.REGISTER_BOOK
                )
        );
    }

    @Test
    @DisplayName("validateAuthorization throws exception when not authenticated")
    void givenInvalidUser_whenAuthorize_thenDecline() {
        MemberRepository memberRepository = new MemberRepository();
        SecurityService securityService = new SecurityService(memberRepository);

        Assertions.assertThrows(
                UnauthorizedException.class, () -> securityService.validateAuthorization(
                        "invaliduser@example.com",
                        Permission.REGISTER_BOOK
                )
        );
    }

}