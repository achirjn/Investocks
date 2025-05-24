package com.investocks.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investocks.entities.Trade;
import com.investocks.repositories.TradeRepo;
import com.investocks.services.TradeServices;

@Service
public class TradeServiceImpl implements TradeServices{

    @Autowired
    private TradeRepo tradeRepository;
    @Override
    public Trade completeTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

}
