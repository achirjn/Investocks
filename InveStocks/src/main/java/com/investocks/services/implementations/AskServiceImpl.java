package com.investocks.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investocks.entities.Ask;
import com.investocks.repositories.AskRepo;
import com.investocks.services.AskServices;

@Service
public class AskServiceImpl implements AskServices{

    @Autowired
    private AskRepo askRepository;
    @Override
    public Ask placeAsk(Ask ask) {
        return askRepository.save(ask);
    }
    @Override
    public List<Ask> askSequenceForCompany(int companyId) {
        return askRepository.findByCompanyByRemainingQtyGreaterThanOrderByPriceAscOrderByPlacedTimeAsc(companyId);
    }
    @Override
    @Transactional
    public void updateAsk(Ask ask) {
        askRepository.updateAsk(ask.getUser().getId(), ask.getCompany().getId(), ask.getPlacedTime(), ask.getRemainingQty());
    }
}
