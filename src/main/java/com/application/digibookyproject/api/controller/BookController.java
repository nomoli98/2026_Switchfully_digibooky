package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.api.mapper.BookMapper;
import com.application.digibookyproject.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }
}
