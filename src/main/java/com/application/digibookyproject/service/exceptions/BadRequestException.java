package com.application.digibookyproject.service.exceptions;

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
}
