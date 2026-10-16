package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class GetOverdueLoansServiceTest {
    private final BookRepository bookRepository = new BookRepository();
    private final LoanRepository loanRepository = new LoanRepository();
    private final BookService bookService = new BookService(bookRepository, loanRepository);
    private final MemberRepository memberRepository = new MemberRepository();
    private final MemberService memberService = new MemberService(memberRepository);
    private LoanService loanService;
    private final Member member = new Member(
            "Ron",
            "Reader",
            "ronreader@reading.com",
            "Read Street",
            "4A",
            8000,
            "Readsville",
            "12-345-6789");

    private final Book book = new Book("isbn", "title", new Author("name", "lastname"));

    private void saveLoansFromNWeeksAgo(int count, int weeks) {
        for (int i = 0; i < count; i++)
            loanRepository.save(
                    new Loan(member, book, LocalDate.now().plusWeeks(weeks))
            );
    }

    @BeforeEach
    void initLoans() {
        saveLoansFromNWeeksAgo(4, 3);
        saveLoansFromNWeeksAgo(6, -3);

        loanService = new LoanService(loanRepository, memberService, bookService);
    }

    @Test
    @DisplayName("6 overdue loans out of 10: getAllOverdueLoans returns 6 loans")
    void givenListOfSixOverdueLoans_whenCallingGetAllOverdueLoans_thenAListOfSize6IsReturned() {
        Assertions.assertThat(loanService.getAllOverdueLoan().size()).isEqualTo(6);
    }
}