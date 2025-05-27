package com.investocks.services;

import java.util.List;

import com.investocks.entities.Bid;


public interface BidServices {
    public Bid placeBid(Bid bid);
    public List<Bid> bidSequenceForCompany(int companyId);
    public void updateBid(Bid bid);
    public List<Bid> pendingBids(int userId);
}
