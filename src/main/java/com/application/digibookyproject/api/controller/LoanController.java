package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.CreateLoanDto;
import com.application.digibookyproject.api.mapper.LoanMapper;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.LoanService;
import com.application.digibookyproject.service.SecurityService;
import com.application.digibookyproject.service.request.PostLoanRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public LoanDto createLoan(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email, @RequestBody PostLoanRequest postLoanRequest) {
        securityService.validateAuthorization(email, Permission.REGISTER_LOAN);
        return loanMapper.mapToDto(loanService.createLoan(postLoanRequest));
    }

    @SecurityRequirement(name = "Authorization")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanDto closeLoan(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email, @PathVariable UUID id) {
        securityService.validateAuthorization(email, Permission.CLOSE_LOAN);
        return loanMapper.mapToDto(loanService.closeLoan(id, email));
    }
}
