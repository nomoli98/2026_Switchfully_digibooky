package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.LoanDto;
import com.application.digibookyproject.api.mapper.LoanMapper;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.LoanService;
import com.application.digibookyproject.service.SecurityService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/loans")
public class MemberLoanController {
    private final LoanService loanService;
    private final LoanMapper loanMapper;
    private final SecurityService securityService;

    public MemberLoanController(LoanService loanService, LoanMapper loanMapper, SecurityService securityService) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
        this.securityService = securityService;
    }

    @GetMapping
    public List<LoanDto> getAllLoansForUser(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email,
            @PathVariable UUID userId
    ) {
        securityService.validateAuthorization(email, Permission.VIEW_LOANS);
        return loanService.getAllLoansForMember(userId).stream()
                .map(loanMapper::mapToDto)
                .toList();
    }
}
