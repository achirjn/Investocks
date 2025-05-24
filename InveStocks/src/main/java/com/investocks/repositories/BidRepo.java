package com.investocks.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.investocks.entities.Bid;

@Repository
public interface BidRepo extends JpaRepository<Bid, Integer>{

    @Query(value="select * from bid where company_id=?1 and remaining_qty>0 order by price desc,placed_time asc;",nativeQuery=true)
    public List<Bid> findByCompanyOrderByPriceDescOrderByPlacedTimeAsc(int companyId);

    @Modifying
    @Query(value="update bid set remaining_qty = ?4 where user_id = ?1 and company_id = ?2 and placed_time = ?3;", nativeQuery=true)
    public void updateBid(int userId, int companyId, LocalDateTime dateTime, int qty);
}

