package com.application.digibookyproject.api.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateLoanDto(UUID id, UUID memberId, UUID bookId, LocalDate dueDate) {
}
