package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.CreateMemberDto;
import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.api.mapper.MemberMapper;
import com.application.digibookyproject.service.MemberService;
import com.application.digibookyproject.service.dto.CreateMemberRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    public MemberController(MemberMapper memberMapper, MemberService memberService) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
    }

    @PostMapping
    public MemberDto createMember(@RequestBody CreateMemberDto createMemberDto) {
        return memberMapper.mapToDto(
                memberService.createMember(new CreateMemberRequest(
                        createMemberDto.firstname(),
                        createMemberDto.lastname(),
                        createMemberDto.email(),
                        createMemberDto.streetname(),
                        createMemberDto.streetnumber(),
                        createMemberDto.postalCode(),
                        createMemberDto.city(),
                        createMemberDto.inss()
                ))
        );
    }
}
