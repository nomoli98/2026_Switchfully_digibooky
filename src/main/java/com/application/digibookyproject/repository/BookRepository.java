package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookRepository extends InMemoryRepository<Book> {

    public Book save(Book book) {
        logger.debug("Saving book with ISBN {}", book.getIsbn());
        return saveWithId(book.getId(), book);
    }
}
