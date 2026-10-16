package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanRepository extends InMemoryRepository<Loan> {
    private final Logger logger = LoggerFactory.getLogger(LoanRepository.class);

    public Loan save(Loan loan) {
        logger.debug("Saving member with id {}", loan.getId());
        return saveWithId(loan.getId(), loan);
    }

    public Optional<Loan> getLoanWithBookId(UUID bookId) {
        return getOptional(loan -> loan.getBook().getId().equals(bookId));
    }

    public List<Loan> getAllLoansWithMemberId(UUID memberId) {
        return getAll().stream()
                .filter(loan -> loan.getMember().getId() == memberId)
                .toList();
    }
}
