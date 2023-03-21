package com.test.scoreboard.controller;

import com.test.scoreboard.domain.GameDTO;
import com.test.scoreboard.domain.Response;
import com.test.scoreboard.exception.TeamNotExistException;
import com.test.scoreboard.service.ScoreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/score")
@AllArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    @GetMapping("/games")
    public ResponseEntity<Response> getGames(@RequestParam(defaultValue = "0", required = false) int page) {
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK).statusCode(OK.value())
                        .message("Games retrieved successfully")
                        .data(scoreService.findAllGames(page))
                        .build()
        );
    }

    @PostMapping("/game/save")
    public ResponseEntity<Response> saveGame(@RequestBody @Valid GameDTO gameDTO) throws TeamNotExistException {
        scoreService.saveGame(gameDTO);
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK).statusCode(OK.value())
                        .message("Game saved successfully")
                        .build()
        );
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Response> getGameById(@PathVariable String id) throws TeamNotExistException {
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK).statusCode(OK.value())
                        .message("Game retrieved successfully")
                        .data(scoreService.findGameCacheById(id))
                        .build()
        );
    }

    @GetMapping("/games/starts")
    public ResponseEntity<Response> findStartGame() {
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK).statusCode(OK.value())
                        .message("Game started successfully")
                        .data(scoreService.findAllGamesCache())
                        .build()
        );
    }

    @PutMapping("/games/update/{home}/{away}")
    public ResponseEntity<Response> updateGame(@RequestParam String id,@PathVariable int home,
                                               @PathVariable int away)
            throws TeamNotExistException {
        scoreService.updateGameScore(id, home, away);
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK).statusCode(OK.value())
                        .message("Game updated successfully")
                        .build()
        );
    }

    @GetMapping("/games/search/{date}")
    public ResponseEntity<Response> searchGame(@PathVariable LocalDate date, @RequestParam int page) {
        return ResponseEntity.ok(
                Response.builder()
                        .status(OK).statusCode(OK.value())
                        .message("Games retrieved successfully")
                        .data(scoreService.findAllGamesByDate(date, page))
                        .build()
        );
    }
}
