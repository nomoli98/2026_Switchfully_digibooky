package com.application.digibookyproject.domain;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String isbn;
    private String title;
    private Author author;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", id=" + id +
                '}';
    }
}
