package com.gamelib.game_lib.service;

import com.gamelib.game_lib.exception.CompanyRequestException;
import com.gamelib.game_lib.model.Country;
import com.gamelib.game_lib.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class CountryServiceImp implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImp(CountryRepository countryRepository) { this.countryRepository = countryRepository; }

    @Override
    public List<Country> getAllCountries() {
        log.info("Get All Countries - Service Call");
        try {
            return countryRepository.findAll();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all countries");
        }
    }
}
