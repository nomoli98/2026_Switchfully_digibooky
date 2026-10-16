package com.application.digibookyproject.api.mapper;

import com.application.digibookyproject.api.dto.LoanDto;
import com.application.digibookyproject.domain.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    public LoanDto mapToDto(Loan loan) {
        return new LoanDto(loan.getId(), loan.getMember().getId(), loan.getBook().getId(), loan.getDueDate());
    }
}
