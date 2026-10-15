package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.api.mapper.MemberMapper;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.MemberService;
import com.application.digibookyproject.service.request.CreateMemberRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final SecurityService securityService;

    public MemberController(
            MemberMapper memberMapper, MemberService memberService,
            SecurityService securityService
    ) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(
            @RequestHeader(value = "Authorization", required = false) String email,
            @RequestBody PostMemberRequest postMemberRequest
    ) {
        securityService.validateAuthorization(email, Permission.REGISTER_LIBRARIAN);
        return memberMapper.mapToDto(memberService.createMember(postMemberRequest));
    }

    @GetMapping
    public List<MemberDto> getMembers(@RequestHeader(value = "Authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.VIEW_MEMBERS);
        return memberService.getAllMembers().stream()
                .map(memberMapper::mapToDto)
                .toList();
    }
}
