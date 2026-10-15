package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.domain.Loan;
import com.application.digibookyproject.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class LoanRepositoryTest {
    private final Member member = new Member(
            "lastname",
            "firstname",
            "email@example.com",
            "streetname",
            "streetnumber",
            0,
            "city",
            "validInss");
    private final Book book = new Book(
            "validIsbn",
            "title",
            new Author(
                    "firstname",
                    "lastname"
            )
    );
    private final Loan loan = new Loan(member, book, LocalDate.now());

    private LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        loanRepository = new LoanRepository();
    }

    @Nested
    @DisplayName("getting a loan with a book id")
    class GetLoan {
        @Test
        @DisplayName("when loan exists returns a Loan")
        void givenLoanExists_whenGettingTheLoanWithABookId_thenReturnLoan() {
            //Given
            loanRepository.save(loan);

            //When
            Loan result = loanRepository.getLoanWithBookId(book.getId()).orElseThrow();

            //Then
            Assertions.assertThat(result).isEqualTo(loan);
        }

        @Test
        @DisplayName("when loan doesn't exist returns an empty optional")
        void givenLoanDoesntExist_whenGettingTheLoanWithABookId_thenReturnEmptyOptional() {
            //Given

            //When

            //Then
            Assertions.assertThat(loanRepository.getLoanWithBookId(book.getId())).isEmpty();
        }
    }
}