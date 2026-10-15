package com.application.digibookyproject.service.request;

public record PostBookRequest(String isbn,
                              String title,
                              String authorFirstname,
                              String authorLastname) { }
