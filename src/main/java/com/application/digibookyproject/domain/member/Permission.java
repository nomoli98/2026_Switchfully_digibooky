package com.application.digibookyproject.domain.member;

import com.application.digibookyproject.service.exception.ForbiddenException;

public enum Permission {
    VIEW_MEMBERS("view members"),
    REGISTER_ADMIN("register a new admin"),
    REGISTER_LIBRARIAN("register a new librarian"),
    REGISTER_BOOK("register a new book"),
    DELETE_BOOK("remove a book"),
    UPDATE_BOOK("update a book"),
    REGISTER_LOAN("register a new loan"),
    CLOSE_LOAN("close a loan"),
    VIEW_LOANS("view loans for a member");

    private final ForbiddenException exception;

    Permission(String description) {
        this.exception = ForbiddenException.forPermission(description);
    }

    public ForbiddenException getException() {
        return exception;
    }
}
