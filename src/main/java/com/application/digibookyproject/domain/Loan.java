package com.application.digibookyproject.domain;

import com.application.digibookyproject.domain.member.Member;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    //public static final int DEFAULT_LOAN_DURATION_IN_WEEKS = 3;

    private final UUID id;
    private final Member member;
    private final Book book;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Member member, Book book, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.member = member;
        this.book = book;
        this.dueDate = dueDate;
        this.returnDate = null;
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

    public LocalDate getReturnDate() { return returnDate;}

    public void setReturnDate() {
        setReturnDate(LocalDate.now());
    }

    public void setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;}



}
