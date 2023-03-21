package com.test.scoreboard.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response {
    private Integer statusCode;
    private HttpStatus status;
    private String message;
    private Object data;
}
