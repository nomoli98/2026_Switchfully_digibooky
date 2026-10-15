package com.application.digibookyproject.service.request;

import java.util.UUID;

public record PutBookRequest(UUID id, String title, String authorFirstName, String authorLastName) {
}
