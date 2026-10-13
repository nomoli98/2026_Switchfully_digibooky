package com.application.digibookyproject.domain;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String isbn;
    private final String title;
    private final Author author;
    private final UUID id;

    public Book(String isbn, String title, Author author) {
        this.author = author;
        this.id = UUID.randomUUID();
        this.isbn = isbn;
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
