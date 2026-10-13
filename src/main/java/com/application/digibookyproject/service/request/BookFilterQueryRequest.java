package com.application.digibookyproject.service.request;

public record BookFilterQueryRequest(String isbn, String title, String firstName, String lastName) {
}
