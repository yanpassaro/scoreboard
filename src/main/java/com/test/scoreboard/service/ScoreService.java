package com.test.scoreboard.service;

import com.test.scoreboard.domain.*;
import com.test.scoreboard.exception.TeamNotExistException;
import com.test.scoreboard.repository.CacheRepository;
import com.test.scoreboard.repository.GameRepository;
import com.test.scoreboard.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Service
@AllArgsConstructor
public class ScoreService {
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final CacheRepository cacheRepository;


    @Transactional
    public void saveGame(GameDTO gameDTO) throws TeamNotExistException {
        Team home = teamRepository.findById(gameDTO.home())
                .orElseThrow(() -> new TeamNotExistException("Team does not exist"));
        Team away = teamRepository.findById(gameDTO.away())
                .orElseThrow(() -> new TeamNotExistException("Team does not exist"));
        Game game = gameRepository.save(
                Game.builder()
                        .home(home)
                        .away(away)
                        .date(gameDTO.date())
                        .kickoff(LocalTime.parse(gameDTO.kickoff()))
                        .start(LocalTime.parse(gameDTO.kickoff()).plusMinutes(30))
                        .stadium(home.getStadium())
                        .awayScore(0)
                        .homeScore(0)
                        .build()
        );
        log.info("Saving game: {}", game);
    }

    @Transactional
    public List<GameResponse> findAllGames(int page){
        log.info("Fetching all games");
        return gameRepository.findAll(PageRequest.of(page, 20)).getContent()
                .stream().map(game -> GameResponse.builder()
                        .away(game.getAway().getName())
                        .home(game.getHome().getName())
                        .date(game.getDate())
                        .kickoff(game.getKickoff())
                        .awayScore(game.getAwayScore())
                        .homeScore(game.getHomeScore())
                        .stadium(game.getStadium())
                        .build()
                ).toList();
    }

    @Transactional
    public List<Game> findAllGamesByDate(LocalDate date, int page){
        log.info("Fetching all games by date: {}", date);
        return gameRepository.findAllByDateIs(date, PageRequest.of(page, 20)).getContent();
    }

    @Transactional
    public Iterable<GameCache> findAllGamesCache(){
        log.info("Fetching all games cache");
        return cacheRepository.findAll();
    }

    @Transactional
    public GameCache findGameCacheById(String id) throws TeamNotExistException {
        log.info("Fetching game cache with id: {}", id);
        return cacheRepository.findById(id)
                .orElseThrow(() -> new TeamNotExistException("Game does not exist"));
    }

    @Transactional
    public void updateGameScore(String id, int homeScore, int awayScore){
        GameCache cache = cacheRepository.findById(id).orElseThrow();
        cache.setHomeScore(homeScore);
        cache.setAwayScore(awayScore);
        cacheRepository.save(cache);
        log.info("Updating game score: {}", cache);
    }

    @Scheduled(fixedDelay = 10, timeUnit = MINUTES)
    public void updateCache() {
        log.info("Updating cache");
        gameRepository.findAllByDateIs(LocalDate.now()).forEach(
                game -> {
                    if (game.getStart() == LocalTime.now())
                        cacheRepository.save(
                                GameCache.builder()
                                        .home(game.getHome().getName())
                                        .away(game.getAway().getName())
                                        .kickoff(game.getKickoff())
                                        .end(game.getKickoff().plusMinutes(90))
                                        .stadium(game.getStadium())
                                        .build()
                        );
                    log.info("Updating cache with game: {}", game);
                }
        );
    }

    @Scheduled(fixedDelay = 90, timeUnit = MINUTES)
    public void deleteCache() {
        log.info("Deleting cache");
        cacheRepository.findAll().forEach(
                gameCache -> {
                    Game game = gameRepository.findById(gameCache.getId()).orElseThrow();
                    if (gameCache.getEnd() == LocalTime.now()) {
                        game.setAwayScore(gameCache.getAwayScore());
                        game.setHomeScore(gameCache.getHomeScore());
                        gameRepository.save(game);
                        cacheRepository.delete(gameCache);
                        log.info("Deleting cache with game: {}", gameCache);
                    }
                }
        );
    }
}
