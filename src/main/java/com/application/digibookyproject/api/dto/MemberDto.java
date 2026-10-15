package com.application.digibookyproject.api.dto;

import java.util.UUID;

public record MemberDto(
        String lastname,
        String firstname,
        String email,
        String streetname,
        String streetnumber,
        int postalCode,
        String city,
        UUID id
) {
}
