package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.CreateLoanDto;
import com.application.digibookyproject.api.dto.MemberDto;
import com.application.digibookyproject.api.mapper.LoanMapper;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.LoanService;
import com.application.digibookyproject.service.SecurityService;
import com.application.digibookyproject.service.request.PostLoanRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final LoanMapper loanMapper;
    private final SecurityService securityService;

    public LoanController(LoanService loanService, LoanMapper loanMapper, SecurityService securityService) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
        this.securityService = securityService;
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLoanDto createLoan(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email,
            @RequestBody PostLoanRequest postLoanRequest
    ) {
        securityService.validateAuthorization(email, Permission.REGISTER_LOAN);
        return loanMapper.mapToDto(loanService.createLoan(postLoanRequest));
    }

    @SecurityRequirement(name = "Authorization")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreateLoanDto closeLoan(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email, @PathVariable UUID id) {
        securityService.validateAuthorization(email, Permission.CLOSE_LOAN);
        return loanMapper.mapToDto(loanService.closeLoan(id, email));
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreateLoanDto> getAllOverdueLoans(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.VIEW_MEMBERS);
        return loanService.getAllOverdueLoan().stream()
                .map(loanMapper::mapToDto)
                .toList();
    }
}
