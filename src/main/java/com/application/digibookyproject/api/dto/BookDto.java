package com.application.digibookyproject.api.dto;

import com.application.digibookyproject.domain.Author;

import java.util.UUID;

public record BookDto(UUID id,
                      String isbn,
                      String title,
                      Author author) {
}
