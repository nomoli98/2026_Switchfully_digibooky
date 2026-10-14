package com.application.digibookyproject.service;

import com.application.digibookyproject.domain.Author;
import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.NotFoundException;
import com.application.digibookyproject.service.request.GetBooksWithFilterRequest;
import com.application.digibookyproject.domain.Book;
import com.application.digibookyproject.repository.BookRepository;
import com.application.digibookyproject.service.predicate.BookPredicate;
import com.application.digibookyproject.service.request.PostBookRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(GetBooksWithFilterRequest queryDto) {
        BookPredicate filter = new BookPredicate(queryDto);
        return bookRepository.getAll().stream().filter(filter::applyFilters).toList();
    }

    public Book getBookById(UUID id) {
        Optional<Book> book = Optional.of(bookRepository.get(id).orElseThrow(() -> NotFoundException.bookNotFound(
                id)));
        return book.get();
    }

    public Book createBook(PostBookRequest request) {

        // TODO : verif isbn

        verifyFilledIn(request.isbn(), "isbn");
        Pattern pattern = Pattern.compile(
                "^(?=(?:\\D*\\d){13}$)97[89]([- ]?)\\d{1,5}\\1\\d{1,7}\\1\\d{1,7}\\1\\d$");
        Matcher matcher = pattern.matcher(request.isbn());
        if (!matcher.find()) throw BadRequestException.invalidIsbn(request.isbn());

        verifyFilledIn(request.title(), "title");
        verifyFilledIn(request.authorFirstname(), "author Firstname");
        verifyFilledIn(request.authorLastname(), "author Lastname");

        Book book = new Book(
                request.isbn(),
                request.title(),
                new Author(request.authorFirstname(), request.authorLastname())
        );
        return bookRepository.save(book);
    }

    private void verifyFilledIn(String value, String parameterName) {
        if (value == null || value.isEmpty())
            throw BadRequestException.bookParameterRequired(parameterName);
    }
}
