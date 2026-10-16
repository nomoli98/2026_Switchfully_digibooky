package com.application.digibookyproject.domain;

import com.application.digibookyproject.domain.member.Member;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    //public static final int DEFAULT_LOAN_DURATION_IN_WEEKS = 3;

    private final UUID id;
    private final Member member;
    private final Book book;
    private final LocalDate dueDate;

    public Loan(Member member, Book book, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.member = member;
        this.book = book;
        this.dueDate = dueDate;
    }

    public UUID getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
