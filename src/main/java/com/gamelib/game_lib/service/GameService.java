package com.gamelib.game_lib.service;

import com.gamelib.game_lib.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {

    List<Game> findAllGames(String stringFilter);

    List<Game> getAllGames();

    Optional<Game> getGameByName(String name);

    void createGame(Game c);

    void deleteGame(Game c);

    long countGames();
}
