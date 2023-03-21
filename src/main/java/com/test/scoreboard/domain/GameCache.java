package com.test.scoreboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalTime;

@RedisHash("games")
@Builder
@Data
public class GameCache {
    @Id
    private String id;
    private String home;
    private String away;
    private int homeScore;
    private int awayScore;
    private String stadium;
    private LocalTime kickoff;
    private LocalTime end;
}
