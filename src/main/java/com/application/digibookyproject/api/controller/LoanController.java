package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.LoanDto;
import com.application.digibookyproject.api.mapper.LoanMapper;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.LoanService;
import com.application.digibookyproject.service.SecurityService;
import com.application.digibookyproject.service.request.PostLoanRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDto createLoan(@RequestBody PostLoanRequest postLoanRequest) {
        return loanMapper.mapToDto(loanService.createLoan(postLoanRequest));
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LoanDto> getAllOverdueLoans(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.VIEW_OVERDUE_LOANS);
        return loanService.getAllOverdueLoans().stream().map(loanMapper::mapToDto).toList();
    }
}
