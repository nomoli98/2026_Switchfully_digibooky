package com.application.digibookyproject.service;

import com.application.digibookyproject.service.dto.BookFilterQueryDto;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.service.filter.BookFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(BookFilterQueryDto queryDto) {
        BookFilter filter = new BookFilter(queryDto);
        return bookRepository.getAll()
                .stream()
                .filter(book -> filter.applyFilters(book))
                .toList();
    }
}
