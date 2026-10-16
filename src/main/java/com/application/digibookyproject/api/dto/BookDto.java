package com.application.digibookyproject.api.dto;

import java.util.UUID;

public record BookDto(
        UUID id,
        String isbn,
        String title,
        AuthorDto authorDto,
        LoanInfoDto loanInfoDto
) {
}
