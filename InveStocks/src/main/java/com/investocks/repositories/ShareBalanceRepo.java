package com.investocks.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.investocks.entities.Company;
import com.investocks.entities.ShareBalance;
import com.investocks.entities.User;


@Repository
public interface ShareBalanceRepo extends JpaRepository<ShareBalance, Integer>{
    public Optional<ShareBalance> findByUserAndCompany(User user,Company company);

    @Modifying
    @Query(value="update share_balance set quantity = quantity + ?3, amountSpent = amountSpent + ?4 where user_id = ?1 and company_id = ?2", nativeQuery=true)
    public void addShareBalance(int userId, int companyId, int balance, float tradeAmount);
    
    
    @Modifying
    @Query(value="update share_balance set quantity = quantity - ?3, amountSpent = amountSpent - ?4 where user_id = ?1 and company_id = ?2", nativeQuery=true)
    public void subtractShareBalance(int userId, int companyId, int balance, float tradeAmount);

    @Query(value="select * from share_balance where user_id=?1;", nativeQuery=true)
    public List<ShareBalance> findByUser(int  userId);
}
