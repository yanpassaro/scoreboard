package com.test.scoreboard.repository;

import com.test.scoreboard.domain.GameCache;
import org.springframework.data.repository.CrudRepository;

public interface CacheRepository extends CrudRepository<GameCache, String> {
}
