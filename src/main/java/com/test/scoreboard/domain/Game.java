package com.test.scoreboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalTime;

@Document("gamesns")
@Data
@Builder
public class Game {
    @Id
    private String id;
    @DocumentReference
    private Team home;
    @DocumentReference
    private Team away;
    private int homeScore;
    private int awayScore;
    private LocalDate date;
    private LocalTime kickoff;
    private LocalTime start;
    private String stadium;
}
