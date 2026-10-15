package com.application.digibookyproject.service.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException bookNotFound(UUID id) {
        return new NotFoundException("The book with the id %s could not be found".formatted(id));
    }

    public static NotFoundException memberNotFound(String email) {
        return new NotFoundException("The member with the email %s could not be found".formatted(email));
    }
}
