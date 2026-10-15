package com.application.digibookyproject.service.request;

import com.application.digibookyproject.domain.member.Role;

public record PostMemberRequest(
        String firstname,
        String lastname,
        String email,
        String streetname,
        String streetnumber,
        int postalCode,
        String city,
        String inss,
        Role role
) { }
