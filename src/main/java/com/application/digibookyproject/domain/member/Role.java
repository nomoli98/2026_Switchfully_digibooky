package com.application.digibookyproject.domain.member;

import java.util.List;

public enum Role {
    ADMIN(List.of(
            Permission.REGISTER_ADMIN,
            Permission.VIEW_MEMBERS,
            Permission.REGISTER_LIBRARIAN
    )),
    LIBRARIAN(List.of(
            Permission.REGISTER_BOOK,
            Permission.UPDATE_BOOK,
            Permission.DELETE_BOOK,
            Permission.VIEW_LOANS
    )),
    MEMBER(List.of());

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
