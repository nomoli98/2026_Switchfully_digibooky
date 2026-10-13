package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.api.dto.QueryDto;
import com.application.digibookyproject.api.mapper.BookMapper;
import com.application.digibookyproject.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<BookDto> getAllBooks(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title) {
        QueryDto queryDto = new QueryDto(isbn, title);
        return bookService.getAllBooks(queryDto)
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }
}
