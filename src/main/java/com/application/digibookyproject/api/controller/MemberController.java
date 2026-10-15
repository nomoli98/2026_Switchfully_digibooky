package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.api.mapper.MemberMapper;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.MemberService;
import com.application.digibookyproject.service.SecurityService;
import com.application.digibookyproject.service.request.PostMemberRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @SecurityRequirement(name = "authorization")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(
            @Parameter(hidden = true) @RequestHeader(value = "authorization", required = false) String email,
            @RequestBody PostMemberRequest postMemberRequest
    ) {
        securityService.validateAuthorization(email, Permission.REGISTER_LIBRARIAN);
        return memberMapper.mapToDto(memberService.createMember(postMemberRequest));
    }

    @SecurityRequirement(name = "authorization")
    @GetMapping
    public List<MemberDto> getMembers(@Parameter(hidden = true) @RequestHeader(value = "authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.VIEW_MEMBERS);
        return memberService.getAllMembers().stream()
                .map(memberMapper::mapToDto)
                .toList();
    }
}
