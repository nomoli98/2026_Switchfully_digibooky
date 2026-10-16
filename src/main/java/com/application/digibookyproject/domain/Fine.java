package com.application.digibookyproject.domain;

import com.application.digibookyproject.service.exception.BadRequestException;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class Fine {
    private final UUID uuid;
    private final Loan loan;

    public Fine(UUID uuid, Loan loan) {
        this.uuid = uuid;
        this.loan = loan;
    }

    public static int calculateFine(LocalDate dueDate){
        if(dueDate.isAfter(LocalDate.now())) throw BadRequestException.loanNotOverdueYet();
        int fullWeeks = Period.between(dueDate, LocalDate.now()).getDays() / 7;
        return 5 + 2 * fullWeeks;
    }
}
