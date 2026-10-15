package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.CreateLoanDto;
import com.application.digibookyproject.domain.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    public CreateLoanDto mapToDto(Loan loan) {
        return new CreateLoanDto(loan.getId(), loan.getMember().getId(), loan.getBook().getId(), loan.getDueDate());
    }
}
