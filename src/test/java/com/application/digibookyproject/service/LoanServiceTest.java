package com.application.digibookyproject.service;

import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanServiceTest {
    LoanService loanService;
    BookService bookService;
    LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        loanRepository = new LoanRepository();
        BookRepository bookRepository = new BookRepository();
        bookService = new BookService(bookRepository, loanRepository);
        loanService = new LoanService(
                loanRepository,
                new MemberService(new MemberRepository()),
                bookService
        );
    }

    @Test
    void test() {

    }

}