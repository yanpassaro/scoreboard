package com.test.scoreboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Reference;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class GameResponse {
    private String home;
    private String away;
    private int homeScore;
    private int awayScore;
    private LocalDate date;
    private LocalTime kickoff;
    private String stadium;
}
