package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepository extends InMemoryRepository<Member> {
    public Member save(Member member) {
        return saveWithId(member.getId(), member);
    }

    public Optional<Member> getMemberWithInss(String inss) {
        return getAll().stream()
                .filter(member -> inss.equals(member.getInss()))
                .findAny();
    }

    public Optional<Member> getMemberWithEmail(String email) {
        return getAll().stream()
                .filter(member -> email.equals(member.getEmail()))
                .findAny();
    }
}
