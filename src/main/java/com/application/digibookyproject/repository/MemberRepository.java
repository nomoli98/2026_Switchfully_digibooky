package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.member.Member;
import com.application.digibookyproject.domain.member.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepository extends InMemoryRepository<Member> {
    private final Logger logger = LoggerFactory.getLogger(MemberRepository.class);

    public Member save(Member member) {
        return saveWithId(member.getId(), member);
    }

    public MemberRepository() {
        logger.debug("Initializing MemberRepository with admin@example.com");
        Member admin = new Member("admin", "admin", "admin@example.com", "", "", 0, "", "");
        admin.setRole(Role.ADMIN);
        save(admin);
    }

    public Optional<Member> getMemberWithInss(String inss) {
        return getOptional(member -> inss.equals(member.getInss()));
    }

    public Optional<Member> getMemberWithEmail(String email) {
        return getOptional(member -> email.equals(member.getEmail()));
    }
}
