package com.test.scoreboard.repository;

import com.test.scoreboard.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
}
