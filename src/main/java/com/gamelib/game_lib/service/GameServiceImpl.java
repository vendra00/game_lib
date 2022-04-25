package com.gamelib.game_lib.service;

import com.gamelib.game_lib.exception.CompanyRequestException;
import com.gamelib.game_lib.model.Game;
import com.gamelib.game_lib.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> findAllGames(String stringFilter) {
        log.info("Get All Games With Filter - Service Call");
        try {
            if (stringFilter == null || stringFilter.isEmpty()) {
                return getAllGames();
            } else {
                return gameRepository.search(stringFilter);
            }
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all games with filter");
        }
    }

    @Override
    public List<Game> getAllGames() {
        log.info("Get All Games - Service Call");
        try {
            return gameRepository.findAll();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all games");
        }
    }

    @Override
    public Optional<Game> getGameByName(String name) {
        //TODO method that get a game by name with optional
        return Optional.empty();
    }

    @Override
    public void createGame(Game game) {
        log.info("Create New Game - Service Call");
        try {
            gameRepository.save(game);
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when creating this Game");
        }
    }

    @Override
    public void deleteGame(Game game) {
        log.info("Delete Company - Service Call");
        try {
            gameRepository.delete(game);
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when deleting this Game");
        }
    }

    @Override
    public long countGames() {
        log.info("Count Games - Service Call");
        try {
            return gameRepository.count();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when counting games");
        }
    }
}
