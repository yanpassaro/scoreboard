package com.test.scoreboard.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record GameDTO(
        String home,
        String away,
        LocalDate date,
        String kickoff
) {
}
