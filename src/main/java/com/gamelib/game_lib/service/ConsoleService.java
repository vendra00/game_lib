package com.gamelib.game_lib.service;

import com.gamelib.game_lib.model.Console;
import com.gamelib.game_lib.model.Game;

import java.util.List;
import java.util.Optional;

public interface ConsoleService {

    List<Console> findAllConsoles(String stringFilter);

    List<Console> getAllConsoles();

    Optional<Console> getConsoleByName(String name);

    Console createConsole(Console c);

    void deleteConsole(Console c);

    long countConsole();
}
