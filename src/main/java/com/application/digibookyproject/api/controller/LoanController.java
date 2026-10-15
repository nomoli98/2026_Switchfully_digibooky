package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.LoanDto;
import com.application.digibookyproject.api.mapper.LoanMapper;
import com.application.digibookyproject.service.LoanService;
import com.application.digibookyproject.service.request.PostLoanRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final LoanMapper loanMapper;

    public LoanController(LoanService loanService, LoanMapper loanMapper) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDto createLoan(@RequestBody PostLoanRequest postLoanRequest) {
        return loanMapper.mapToDto(loanService.createLoan(postLoanRequest));
    }
}
