package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.ConflictException;
import com.application.digibookyproject.service.request.PostMemberRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
        if (!request.email().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw BadRequestException.invalidEmail(request.email());

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

    public List<Member> getAllMembers() {
        return memberRepository.getAll();
    }
}
