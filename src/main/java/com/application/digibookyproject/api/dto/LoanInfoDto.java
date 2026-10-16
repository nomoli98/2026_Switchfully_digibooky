package com.application.digibookyproject.api.dto;

import java.time.LocalDate;
import java.util.UUID;

public record LoanInfoDto(
        UUID memberId,
        UUID loanId,
        String isbn,
        LocalDate dueDate
) {
}
