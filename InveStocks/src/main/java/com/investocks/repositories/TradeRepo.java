package com.investocks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.investocks.entities.Trade;

@Repository
public interface TradeRepo extends JpaRepository<Trade, Integer>{

}
