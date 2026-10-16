package com.application.digibookyproject.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class Fine {
    private final UUID id;
    private final Loan loan;
    private int amount;

    public Fine(Loan loan) {
        this.id = UUID.randomUUID();
        this.loan = loan;
        this.amount = calculateFine(loan.getDueDate());
    }

    public UUID getId() {
        return id;
    }

    public Loan getLoan() {
        return loan;
    }

    public int getAmount() {
        return amount;
    }

    public void updateAmount() {
        this.amount = calculateFine(loan.getDueDate());
    }

    public int calculateFine(LocalDate dueDate) {
        return calculateFine(dueDate, LocalDate.now());
    }

    public int calculateFine(LocalDate dueDate, LocalDate returnDate){
        int fullWeeks = Period.between(dueDate, returnDate).getDays() / 7;
        return 5 + 2 * fullWeeks;
    }
}
