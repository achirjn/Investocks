package com.investocks.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investocks.entities.Company;
import com.investocks.repositories.CompanyRepo;
import com.investocks.services.CompanyServices;

@Service
public class CompanyServiceImpl implements CompanyServices{

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public List<Company> getCompanyList() {
        return companyRepo.findAll();
    }

    @Override
    public Optional<Company> getCompanyByName(String name) {
        return companyRepo.findByName(name);
    }
    @Override
    public Optional<Company> getCompanyById(int id) {
        return companyRepo.findById(id);
    }

    @Transactional
    @Override
    public void updateCompanyOnTrade(Company company, int price) {
        float profit = (price - company.getClosedPrice()) / company.getClosedPrice();
        companyRepo.updateCompanyOnTrade(company.getId(), price, profit);
    }

}
