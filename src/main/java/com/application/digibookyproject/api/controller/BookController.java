package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.service.request.BookFilterQueryRequest;
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
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        BookFilterQueryRequest bookFilterQueryDto = new BookFilterQueryRequest(isbn, title, firstName, lastName);
        return bookService.getAllBooks(bookFilterQueryDto)
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public BookDto getBookById(){
        return null;
    }
}
