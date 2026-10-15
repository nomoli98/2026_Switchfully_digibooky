package com.application.digibookyproject.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    public static final int DEFAULT_LOAN_DURATION_IN_WEEKS = 3;

    private final UUID id;
    private final UUID memberId;
    private final UUID bookId;
    private final LocalDate dueDate;

    public Loan(UUID memberId, UUID bookId, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.memberId = memberId;
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
