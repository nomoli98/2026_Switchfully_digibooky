package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRepository extends InMemoryRepository<Loan> {
    private final Logger logger = LoggerFactory.getLogger(LoanRepository.class);

    public Loan save(Loan loan) {
        logger.debug("Saving member with id {}", loan.getId());
        return saveWithId(loan.getId(), loan);
    }
}
