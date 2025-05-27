package com.investocks.services;

import java.util.List;
import java.util.Optional;

import com.investocks.entities.Company;

public interface CompanyServices {

    public List<Company> getCompanyList();
    public Optional<Company> getCompanyByName(String name);
    public Optional<Company> getCompanyById(int id);
    public void updateCompanyOnTrade(Company company, int price);
}
