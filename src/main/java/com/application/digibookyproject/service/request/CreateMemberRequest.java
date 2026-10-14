package com.application.digibookyproject.service.request;

public record CreateMemberRequest(
        String firstname,
        String lastname,
        String email,
        String streetname,
        String streetnumber,
        int postalCode,
        String city,
        String inss
) {
}
