package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;
import com.application.digibookyproject.api.mapper.BookMapper;
import com.application.digibookyproject.service.BookService;
import com.application.digibookyproject.service.request.PostBookRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
            @RequestParam(required = false) String lastName
                                    ) {
        GetBooksWithFilterRequest bookFilterQueryDto = new GetBooksWithFilterRequest(
                isbn,
                title,
                firstName,
                lastName
        );
        return bookService.getAllBooks(bookFilterQueryDto)
                          .stream()
                          .map(bookMapper::mapToDto)
                          .toList();

    }

    @GetMapping(path = "/{id}")
    public BookDto getBookById(@PathVariable UUID id) {
        return bookMapper.mapToDto(bookService.getBookById(id));
    }

    @PostMapping
    public BookDto registerBook(@RequestBody PostBookRequest postBookRequest) {
        return bookMapper.mapToDto(bookService.createBook(postBookRequest));
    }

    @DeleteMapping(path = "/{id}")
    public BookDto deleteBook(@PathVariable UUID id) {
        return bookMapper.mapToDto(bookService.deleteBook(id));
    }
}
