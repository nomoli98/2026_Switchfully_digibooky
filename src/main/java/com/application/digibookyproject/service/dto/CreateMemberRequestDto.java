package com.application.digibookyproject.service.dto;

public record CreateMemberRequestDto (
        String firstname,
        String lastname,
        String email,
        String streetname,
        String streetnumber,
        int postalCode,
        String city,
        String inss
){
}
