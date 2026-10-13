package com.application.digibookyproject.service.exceptions;

public class ConflictException extends RuntimeException {
    private ConflictException(String message) {
        super(message);
    }

    public static ConflictException duplicateEmail(String email) {
        return new ConflictException("A user with email %s already exists.".formatted(email));
    }

    public static ConflictException duplicateInss(String inss) {
        return new ConflictException("A user with inss number %s already exists.".formatted(inss));
    }

}
