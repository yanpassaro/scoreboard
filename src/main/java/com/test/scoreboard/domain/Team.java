package com.test.scoreboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("teamns")
@Data
@Builder
public class Team {
    @Id
    private String id;
    private String name;
    private int titles;
    private String stadium;
}
