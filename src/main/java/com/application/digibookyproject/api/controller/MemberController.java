package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.api.mapper.MemberMapper;
import com.application.digibookyproject.service.MemberService;
import com.application.digibookyproject.service.request.CreateMemberRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        return memberMapper.mapToDto(memberService.createMember(createMemberRequest));
    }
}
