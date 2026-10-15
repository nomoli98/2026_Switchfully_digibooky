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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(GetBooksWithFilterRequest queryDto) {
        BookPredicate filter = new BookPredicate(queryDto);
        return bookRepository.getAll()
                .stream()
                .filter(filter::applyFilters)
                .toList();
    }

    public Book getBookById(UUID id) {
        Optional<Book> book = Optional.of(bookRepository.get(id).orElseThrow(() -> NotFoundException.bookNotFound(
                id)));
        return book.get();
    }

    private boolean checkIsbn(String isbn) {
        if (isbn.length() != 17) return false;
        String[] array = isbn.split("-");
        if (array.length != 5) return false;
        if (!array[0].equals("978") && !array[0].equals("979")) return false;
        return Arrays.stream(array)
                .allMatch(part -> part.matches("\\d+"));
    }

    public Book createBook(PostBookRequest request) {

        verifyFilledIn(request.isbn(), "isbn");
        if (!checkIsbn(request.isbn())) throw BadRequestException.invalidIsbn(request.isbn());
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

    public Book deleteBook(UUID id) {
        return bookRepository.deleteWithId(id, getBookById(id));
    }

    private void verifyFilledIn(String value, String parameterName) {
        if (value == null || value.isEmpty())
            throw BadRequestException.bookParameterRequired(parameterName);
    }
}
