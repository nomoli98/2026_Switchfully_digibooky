package com.application.digibookyproject.service;

import com.application.digibookyproject.api.dto.QueryDto;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(QueryDto queryDto) {
        Stream<Book> books = bookRepository.getAll().stream();

        if (queryDto.getIsbn() != null)
            books = books.filter(b-> b.getIsbn().equals(queryDto.getIsbn()));
        if (queryDto.getTitle() != null)
            books = books.filter(b-> b.getTitle().contains(queryDto.getTitle()));

        return books.toList();
    }


}
