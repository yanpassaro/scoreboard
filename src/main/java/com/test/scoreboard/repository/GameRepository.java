package com.test.scoreboard.repository;

import com.test.scoreboard.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findAllByDateIs(LocalDate date);
    Page<Game> findAllByDateIs(LocalDate date, Pageable pageable);
}
