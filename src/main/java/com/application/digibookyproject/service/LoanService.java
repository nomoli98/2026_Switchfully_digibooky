package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.repository.LoanRepository;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.NotFoundException;
import com.application.digibookyproject.service.request.PostLoanRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final MemberService memberService;
    private final BookService bookService;
    public static final int DEFAULT_LOAN_DURATION_IN_WEEKS = 3;

    public LoanService(LoanRepository loanRepository, MemberService memberService, BookService bookService) {
        this.loanRepository = loanRepository;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    public List<Loan> getAllLoansForMember(UUID memberId) {
        return loanRepository.getAllLoansWithMemberId(memberId);
    }

    public Loan createLoan(PostLoanRequest request) {
        verifyFilledIn(request.memberEmail(), "member email");
        if (request.bookId() == null)
            throw BadRequestException.loanParameterRequired("book id");
        Book book = bookService.getBookById(request.bookId());
        if (!isAvailable(book))
            throw NotFoundException.bookNotFound(request.bookId());
        Member member = memberService.getAllMembers().stream()
                .filter(m -> m.getEmail().equals(request.memberEmail()))
                .findFirst()
                .orElseThrow(() -> NotFoundException.memberNotFound(request.memberEmail()));

        Loan loan = new Loan(
                member,
                book,
                LocalDate.now().plusWeeks(DEFAULT_LOAN_DURATION_IN_WEEKS)
        );
        return loanRepository.save(loan);
    }

    public Loan closeLoan(UUID loanId, String email){
        Loan existingLoan = loanRepository.get(loanId).orElseThrow(() -> NotFoundException.loanNotFound(loanId));
        if (!existingLoan.getMember().getEmail().equals(email)) throw BadRequestException.notMatchingUser();
        return loanRepository.deleteWithId(loanId, existingLoan);
    }

    public List<Loan> getAllOverdueLoan(){
        return loanRepository.getAll().stream().filter(l -> l.getDueDate().isBefore(LocalDate.now())).toList();
    }

    private void verifyFilledIn(String value, String parameterName) {
        if (value == null || value.isEmpty())
            throw BadRequestException.loanParameterRequired(parameterName);
    }

    private boolean isAvailable(Book book) {
        return loanRepository.getLoanWithBookId(book.getId()).isEmpty();
    }
}
