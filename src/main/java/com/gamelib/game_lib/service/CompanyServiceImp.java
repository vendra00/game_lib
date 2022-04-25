package com.gamelib.game_lib.service;

import com.gamelib.game_lib.exception.CompanyRequestException;
import com.gamelib.game_lib.model.Company;
import com.gamelib.game_lib.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImp implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImp(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAllCompanies(String stringFilter) {
        log.info("Get All Companies With Filter - Service Call");
        try {
            if (stringFilter == null || stringFilter.isEmpty()) {
                return getAllCompanies();
            } else {
                return companyRepository.search(stringFilter);
            }
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all companies with filter");
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        log.info("Get All Companies - Service Call");
        try {
            return companyRepository.findAll();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when fetching all companies");
        }
    }

    @Override
    public Optional<Company> getCompanyByName(String name) {
        return Optional.empty();
    }

    @Override
    public void createCompany(Company c) {
        log.info("Create New Company - Service Call");
        try {
            companyRepository.save(c);
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when creating this Company");
        }
    }

    @Override
    public void deleteCompany(Company c) {
        log.info("Delete Company - Service Call");
        try {
            companyRepository.delete(c);
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when deleting this Company");
        }
    }

    @Override
    public long countCompanies() {
        log.info("Count Companies - Service Call");
        try {
            return companyRepository.count();
        } catch (Exception e) {
            log.error("ERROR : " + e);
            throw new CompanyRequestException("There was a problem when counting companies");
        }
    }
}
