package com.application.digibookyproject.repository;

import com.application.digibookyproject.domain.Fine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FineRepository extends InMemoryRepository<Fine> {
    private final Logger logger = LoggerFactory.getLogger(FineRepository.class);

    public Fine save(Fine fine){
        logger.debug("Saving fine with id {}", fine.getId());
        return saveWithId(fine.getId(), fine);
    }



}
