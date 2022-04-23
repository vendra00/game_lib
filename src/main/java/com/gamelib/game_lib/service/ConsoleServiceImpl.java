package com.gamelib.game_lib.service;

import com.gamelib.game_lib.exception.CompanyRequestException;
import com.gamelib.game_lib.model.Console;
import com.gamelib.game_lib.repository.ConsoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class ConsoleServiceImpl implements ConsoleService{

    private final ConsoleRepository consoleRepository;

    public ConsoleServiceImpl(ConsoleRepository consoleRepository) {
        this.consoleRepository = consoleRepository;
    }

    @Override
    public List<Console> findAllConsoles(String stringFilter) {
        log.info("Get All Consoles With Filter - Service Call");
        try {
            if (stringFilter == null || stringFilter.isEmpty()) {
                return consoleRepository.findAll();
            } else {
                return consoleRepository.search(stringFilter);
            }
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all companies with filter");
        }
    }

    @Override
    public List<Console> getAllConsoles() {
        log.info("Get All Consoles - Service Call");
        try {
            return consoleRepository.findAll();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all consoles");
        }
    }

    @Override
    public Optional<Console> getConsoleByName(String name) {
        return Optional.empty();
    }

    @Override
    public Console createConsole(Console c) {
        log.info("Create New Console - Service Call");
        try {
            return consoleRepository.save(c);
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when creating this console");
        }
    }

    @Override
    public void deleteConsole(Console c) {
        log.info("Delete Console - Service Call");
        try {
            consoleRepository.delete(c);
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when deleting this console");
        }

    }

    @Override
    public long countConsole() {
        log.info("Count Consoles - Service Call");
        try {
            return consoleRepository.count();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when counting consoles");
        }
    }
}
