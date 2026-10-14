package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final MemberRepository memberRepository;

    public SecurityService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void validateAuthorization(String email, Permission permission) {
        if (email == null) throw UnauthorizedException.invalidCredentials();

        Member member = memberRepository.getMemberWithEmail(email)
                                        .orElseThrow(UnauthorizedException::invalidCredentials);

        if (!member.hasPermission(permission)) throw permission.getException();
    }
}
