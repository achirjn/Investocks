package com.investocks.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.investocks.entities.Company;


@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer>{
    public Optional<Company> findByName(String name);
    public Optional<Company> findById(int id);


    @Modifying
    @Query(value="update company_info set current_price = ?2 , profit = ?3 where id = ?1;", nativeQuery=true)
    public void updateCompanyOnTrade(int id, int price, float profit);
}
