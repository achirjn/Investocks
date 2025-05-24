package com.investocks.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investocks.entities.Company;
import com.investocks.entities.ShareBalance;
import com.investocks.entities.User;
import com.investocks.repositories.ShareBalanceRepo;
import com.investocks.services.ShareBalanceServices;

@Service
public class ShareBalanceServiceImpl implements ShareBalanceServices{

    @Autowired
    private ShareBalanceRepo shareBalanceRepository;
    @Override
    public Optional<ShareBalance> getBalanceOfUserForCompany(User user, Company company) {
        return shareBalanceRepository.findByUserAndCompany(user, company);
    }
    @Override
    @Transactional
    public void addBalance(User user, Company company, int balance) {
        shareBalanceRepository.addShareBalance(user.getId(), company.getId(), balance);
    }
    @Override
    @Transactional
    public void  subtractBalance(User user, Company company, int balance) {
        shareBalanceRepository.subtractShareBalance(user.getId(), company.getId(), balance);
    }
    @Override
    public ShareBalance newBalanceEntry(ShareBalance shareBalance) {
        return shareBalanceRepository.save(shareBalance);
    }

}
