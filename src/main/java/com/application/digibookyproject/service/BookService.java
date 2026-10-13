package com.application.digibookyproject.service;

import com.application.digibookyproject.service.exception.NotFoundException;
import com.application.digibookyproject.service.request.BookFilterQueryRequest;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.service.filter.BookFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(BookFilterQueryRequest queryDto) {
        BookFilter filter = new BookFilter(queryDto);
        return bookRepository.getAll()
                             .stream()
                             .filter(book -> filter.applyFilters(book))
                             .toList();
    }

    public Book getBookById(UUID id) {
        Optional<Book> book = Optional.of(bookRepository.get(id).orElseThrow(() -> NotFoundException.bookNotFound(
                id)));
        return book.get();
    }
}
