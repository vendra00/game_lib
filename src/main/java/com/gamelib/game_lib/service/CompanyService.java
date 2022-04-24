package com.gamelib.game_lib.service;

import com.gamelib.game_lib.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> findAllCompanies(String stringFilter);

    List<Company> getAllCompanies();

    Optional<Company> getCompanyByName(String name);

    void createCompany(Company c);

    void deleteCompany(Company c);

    long countCompanies();
}
