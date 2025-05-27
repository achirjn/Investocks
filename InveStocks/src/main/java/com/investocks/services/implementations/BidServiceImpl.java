package com.investocks.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investocks.entities.Bid;
import com.investocks.repositories.BidRepo;
import com.investocks.services.BidServices;

@Service
public class BidServiceImpl implements BidServices{

    @Autowired
    private BidRepo bidRepository;
    @Override
    public Bid placeBid(Bid bid) {
        return bidRepository.save(bid);
    }
    @Override
    public List<Bid> bidSequenceForCompany(int companyId) {
        return bidRepository.findByCompanyOrderByPriceDescOrderByPlacedTimeAsc(companyId);
    }
    @Override
    @Transactional
    public void updateBid(Bid bid) {
        bidRepository.updateBid(bid.getUser().getId(), bid.getCompany().getId(), bid.getPlacedTime(), bid.getRemainingQty());
    }

    @Override
    public List<Bid> pendingBids(int userId){
        return bidRepository.pendingBidsOfUser(userId);
    }

}
