package com.application.digibookyproject.repository;


import com.application.digibookyproject.domain.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository extends InMemoryRepository<Book> {

    public Book save(Book book) {
        return saveWithId(book.getId(), book);
    }
}
