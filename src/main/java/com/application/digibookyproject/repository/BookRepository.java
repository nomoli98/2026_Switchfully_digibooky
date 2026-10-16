package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;

@Repository
public class BookRepository extends InMemoryRepository<Book> {

    public BookRepository() {
        super(Book.class);
    }

    public Book save(Book book) {
        return saveWithId(book.getId(), book);
    }

    public List<Book> getAllBooksWith(Predicate<Book> bookPredicate) {
        return getAllWhere(bookPredicate);
    }
}
