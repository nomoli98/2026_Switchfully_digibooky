package com.application.digibookyproject.factory;

import com.application.digibookyproject.domain.Member;
import com.application.digibookyproject.repository.MemberRepository;

import java.util.List;

public class MemberTestData {
    public static MemberRepository createRepository(List<Member> presentMembers) {
        MemberRepository repository = new MemberRepository();
        repository.save(new Member("Peeters", "Jan", "jan.peeters@example.com", "Kerkstraat", "12", 9000, "Gent", "85.04.15-123.45"));
        repository.save(new Member("Janssens", "Marie", "marie.janssens@example.com", "Stationsstraat", "24", 2800, "Mechelen", "90.07.22-234.56"));
        repository.save(new Member("Dubois", "Lucas", "lucas.dubois@example.com", "Rue de la Loi", "105", 1000, "Brussel", "88.11.03-345.67"));
        repository.save(new Member("Willems", "Sofie", "sofie.willems@example.com", "Langestraat", "7A", 9300, "Aalst", "95.02.18-456.78"));
        repository.save(new Member("Maes", "Thomas", "thomas.maes@example.com", "Markt", "3", 9700, "Oudenaarde", "92.09.11-567.89"));
        repository.save(new Member("Lambert", "Emma", "emma.lambert@example.com", "Leuvensesteenweg", "156", 1930, "Zaventem", "01.06.29-678.90"));
        repository.save(new Member("Vermeulen", "Pieter", "pieter.vermeulen@example.com", "Dorpstraat", "42", 9500, "Geraardsbergen", "86.12.05-789.01"));

        presentMembers.forEach(repository::save);
        return repository;
    }
}
