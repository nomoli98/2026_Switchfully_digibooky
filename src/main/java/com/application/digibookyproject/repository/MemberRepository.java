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
        return getOptional(member -> inss.equals(member.getInss()));
    }

    public Optional<Member> getMemberWithEmail(String email) {
        return getOptional(member -> email.equals(member.getEmail()));
    }
}
