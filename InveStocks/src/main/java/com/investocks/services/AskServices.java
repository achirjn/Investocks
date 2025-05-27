package com.investocks.services;

import java.util.List;

import com.investocks.entities.Ask;

public interface AskServices {
    public Ask placeAsk(Ask ask);
    public List<Ask> askSequenceForCompany(int companyId);
    public void updateAsk(Ask ask);
    public List<Ask> pendingAsks(int userId);
}
