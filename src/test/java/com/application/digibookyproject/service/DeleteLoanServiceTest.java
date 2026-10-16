package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.domain.member.Role;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.repository.MemberRepository;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.NotFoundException;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;
import com.application.digibookyproject.service.request.PostBookRequest;
import com.application.digibookyproject.service.request.PostLoanRequest;
import com.application.digibookyproject.service.request.PostMemberRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteLoanServiceTest {
    private final BookRepository bookRepository = new BookRepository();
    private final LoanRepository loanRepository = new LoanRepository();
    private final BookService bookService = new BookService(bookRepository, loanRepository);
    private final MemberRepository memberRepository = new MemberRepository();
    private final MemberService memberService = new MemberService(memberRepository);
    private final LoanService loanService = new LoanService(loanRepository, memberService, bookService);
    private Loan loan;

    @BeforeEach
    void initRepository() {
        PostBookRequest bookRequest = new PostBookRequest(
                "978-1-1234-5678-9",
                "le title",
                "le author firstname",
                "le author last name");
        bookService.createBook(bookRequest);
        PostMemberRequest memberRequest = new PostMemberRequest(
                "Ron",
                "Reader",
                "ronreader@reading.com",
                "Read Street",
                "4A",
                8000,
                "Readsville",
                "12-345-6789",
                Role.MEMBER);
        memberService.createMember(memberRequest);
        Book book = bookService.getAllBooks(new GetBooksWithFilterRequest(null, null, null, null)).getFirst();
        Member member = memberService.getAllMembers().stream().filter(m->m.getFirstname().equals("Ron")).findAny().get();
        PostLoanRequest request = new PostLoanRequest(member.getEmail(), book.getId());
        loan = loanService.createLoan(request);
    }

    @Test
    @DisplayName("deleting an existing loan returns the loan")
    void givenAnExistingLoanId_WhenDeleted_ReturnsLoanObject() {
        assertEquals(loan, loanService.closeLoan(loan.getId(), loan.getMember().getEmail()));
    }

    @Test
    @DisplayName("deleting an existing loan deletes it correctly")
    void givenAnExistingLoanId_WhenDeleted_ThenTheLoanIsGone() {
        assertEquals(loan, loanService.closeLoan(loan.getId(), loan.getMember().getEmail()));
        Assertions.assertThat(loanRepository.getAll()).isEmpty();
    }

    @Test
    @DisplayName("deleting an non existing loan throws a notFoundException")
    void givenAnNonExistingLoanId_WhenDeleted_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> loanService.closeLoan(UUID.randomUUID(), "admin@example.com"));
    }

    @Test
    @DisplayName("deleting a loan that is not the user's throws a badRequestException")
    void givenAnLoanThatIsNotTheUsers_WhenDeleted_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> loanService.closeLoan(loan.getId(), "admin@example.com"));
    }

}