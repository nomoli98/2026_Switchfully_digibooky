package com.application.digibookyproject.service.exception;

public class BadRequestException extends RuntimeException {
    private BadRequestException(String message) {
        super(message);
    }

    public static BadRequestException memberParameterRequired(String parameter) {
        return new BadRequestException("Member parameter %s is required".formatted(parameter));
    }

    public static BadRequestException invalidEmail(String email) {
        return new BadRequestException("Email %s doesn't match pattern x@x.x".formatted(email));
    }

    public static BadRequestException bookParameterRequired(String parameter) {
        return new BadRequestException("Book parameter %s is required".formatted(parameter));
    }

    public static BadRequestException invalidIsbn(String isbn) {
        return new BadRequestException("Given ISBN %s doesn't match ISBN pattern".formatted(isbn));
    }

    public static BadRequestException loanParameterRequired(String parameter) {
        return new BadRequestException("Loan parameter %s is required".formatted(parameter));
    }
}
