package com.application.digibookyproject.service.request;

public record GetBooksWithFilterRequest(String isbn,
                                        String title,
                                        String firstName,
                                        String lastName) { }
