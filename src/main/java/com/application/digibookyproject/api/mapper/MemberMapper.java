package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public MemberDto mapToDto(Member member) {
        return new MemberDto(
                member.getLastname(),
                member.getFirstname(),
                member.getEmail(),
                member.getStreetname(),
                member.getStreetnumber(),
                member.getPostalCode(),
                member.getCity(),
                member.getInss(),
                member.getId()
        );
    }
}
