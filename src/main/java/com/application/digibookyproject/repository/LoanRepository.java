package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanRepository extends InMemoryRepository<Loan> {
    public LoanRepository() {
        super(Loan.class);
    }

    public Loan save(Loan loan) {
        return saveWithId(loan.getId(), loan);
    }

    public Optional<Loan> getLoanWithBookId(UUID bookId) {
        return getOptional(loan -> loan.getBook().getId().equals(bookId));
    }

    public List<Loan> getAllLoansWithMemberId(UUID memberId) {
        return getAllWhere(loan -> loan.getMember().getId().equals(memberId));
    }
}
