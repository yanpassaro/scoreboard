package com.test.scoreboard.exception;

import com.test.scoreboard.domain.Response;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalHandler {

    @ExceptionHandler(value = TeamNotExistException.class)
    public ResponseEntity<Response> handleIncorrectCredentialsException(TeamNotExistException ex) {
        return ResponseEntity.badRequest().body(
                Response.builder()
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }
}
