package com.application.digibookyproject.service.request;

public record PostMemberRequest(
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
