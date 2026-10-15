package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.request.PostMemberRequest;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.ConflictException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final EmailValidator emailValidator;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        emailValidator = EmailValidator.getInstance();
    }

    public Member createMember(
            PostMemberRequest request
    ) {
        verifyFilledIn(request.email(), "email");
        verifyFilledIn(request.inss(), "inss");
        verifyFilledIn(request.firstname(), "firstname");
        verifyFilledIn(request.city(), "city");
        if (memberRepository.getMemberWithEmail(request.email()).isPresent())
            throw ConflictException.duplicateEmail(request.email());
        if (memberRepository.getMemberWithInss(request.inss()).isPresent())
            throw ConflictException.duplicateInss(request.inss());
        if (!emailValidator.isValid(request.email())) throw BadRequestException.invalidEmail(request.email());

        Member member = new Member(
                request.lastname(),
                request.firstname(),
                request.email(),
                request.streetname(),
                request.streetnumber(),
                request.postalCode(),
                request.city(),
                request.inss()
        );
        return memberRepository.save(member);
    }

    private void verifyFilledIn(String value, String parameterName) {
        if (value == null || value.isEmpty())
            throw BadRequestException.memberParameterRequired(parameterName);
    }
}
