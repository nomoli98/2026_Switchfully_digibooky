package com.application.digibookyproject.service.exception;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(UUID id) {
        super("No book found with id " + id);
    }
}
