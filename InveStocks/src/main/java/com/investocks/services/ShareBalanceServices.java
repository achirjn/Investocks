package com.investocks.services;

import java.util.Optional;

import com.investocks.entities.Company;
import com.investocks.entities.ShareBalance;
import com.investocks.entities.User;

public interface ShareBalanceServices {
    public Optional<ShareBalance> getBalanceOfUserForCompany(User user, Company company);
    public void addBalance(User user, Company company, int balance);
    public void subtractBalance(User user, Company company, int balance);
    public ShareBalance newBalanceEntry(ShareBalance shareBalance);
}
