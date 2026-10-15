package com.application.digibookyproject.api.controller;

import com.application.digibookyproject.api.dto.BookDto;
import com.application.digibookyproject.domain.member.Permission;
import com.application.digibookyproject.service.SecurityService;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;
import com.application.digibookyproject.api.mapper.BookMapper;
import com.application.digibookyproject.service.BookService;
import com.application.digibookyproject.service.request.PostBookRequest;
import com.application.digibookyproject.service.request.PutBookRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final SecurityService securityService;

    public BookController(BookService bookService, BookMapper bookMapper, SecurityService securityService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.securityService = securityService;
    }

    @GetMapping
    public List<BookDto> getAllBooks(@RequestParam(required = false) String isbn, @RequestParam(required = false) String title, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        GetBooksWithFilterRequest bookFilterQueryDto = new GetBooksWithFilterRequest(isbn, title, firstName, lastName);
        return bookService.getAllBooks(bookFilterQueryDto).stream().map(bookMapper::mapToDto).toList();

    }

    @GetMapping(path = "/{id}")
    public BookDto getBookById(@PathVariable UUID id) {
        return bookMapper.mapToDto(bookService.getBookById(id));
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping
    public BookDto registerBook(@RequestBody PostBookRequest postBookRequest, @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.REGISTER_BOOK);
        return bookMapper.mapToDto(bookService.createBook(postBookRequest));
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping
    public BookDto updateBook(@RequestBody PutBookRequest putBookRequest, @Parameter(hidden = true)  @RequestHeader(value = "Authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.UPDATE_BOOK);
        return bookMapper.mapToDto(bookService.updateBook(putBookRequest));
    }

    @SecurityRequirement(name = "Authorization")
    @DeleteMapping(path = "/{id}")
    public BookDto deleteBook(@PathVariable UUID id, @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String email) {
        securityService.validateAuthorization(email, Permission.DELETE_BOOK);
        return bookMapper.mapToDto(bookService.deleteBook(id));
    }
}
