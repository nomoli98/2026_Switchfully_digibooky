package com.application.digibookyproject.api;

import com.application.digibookyproject.service.exception.BadRequestException;
import com.application.digibookyproject.service.exception.BookNotFoundException;
import com.application.digibookyproject.service.exception.ConflictException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.UUID;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    // Expected client error → WARN, message is enough (no stack trace)
    @ExceptionHandler(BookNotFoundException.class)
    protected void bookNotFound(BookNotFoundException ex, HttpServletResponse response) throws IOException {
        logger.warn("Book not found: {}", ex.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    protected void badRequest(BadRequestException ex, HttpServletResponse response) throws IOException {
        logger.warn("Bad Request: {}", ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    protected void conflict(ConflictException ex, HttpServletResponse response) throws IOException {
        logger.warn("Conflict: {}", ex.getMessage());
        response.sendError(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    // Unexpected error → ERROR + stack trace + unique ID the client can report
    @ExceptionHandler(Exception.class)
    protected void unexpectedError(Exception ex, HttpServletResponse response) throws IOException {
        String errorId = UUID.randomUUID().toString();
        logger.error("Unexpected error: {}", errorId, ex);   // exception LAST = stack trace included
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                           "Something went wrong. Please contact support with code: " + errorId);
    }
}
