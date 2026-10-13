package com.application.digibookyproject.api.dto;

public record CreateMemberDto (
        String lastname,
        String firstname,
        String email,
        String streetname,
        String streetnumber,
        int postalCode,
        String city,
        String inss
){
}
