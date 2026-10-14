package com.application.digibookyproject.domain;

import java.util.UUID;

public class Member {
    private final String lastname;
    private final String firstname;
    private final String email;
    private final String streetname;
    private final String streetnumber;
    private final int postalCode;
    private final String city;
    private final String Inss;
    private final UUID id;

    public Member(
            String lastname,
            String firstname,
            String email,
            String streetname,
            String streetnumber,
            int postalCode,
            String city,
            String inss
                 ) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetname = streetname;
        this.streetnumber = streetnumber;
        this.postalCode = postalCode;
        this.city = city;
        Inss = inss;
        this.id = UUID.randomUUID();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getStreetname() {
        return streetname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getInss() {
        return Inss;
    }

    public UUID getId() {
        return id;
    }
}
