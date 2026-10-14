package com.application.digibookyproject.service.exception;

public class UnauthorizedException extends RuntimeException {

    private UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException unauthorized() {
        return new UnauthorizedException("Unauthorized");
    }

    public static UnauthorizedException invalidCredentials() {
        return new UnauthorizedException("Invalid Credentials");
    }

}
