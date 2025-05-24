package com.investocks.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.investocks.entities.Ask;


@Repository
public interface AskRepo extends JpaRepository<Ask, Integer>{

    @Query(value="select * from ask where company_id = :company_id and remaining_qty > 0 order by price asc, placed_time asc;", nativeQuery=true)
    public List<Ask> findByCompanyByRemainingQtyGreaterThanOrderByPriceAscOrderByPlacedTimeAsc(@Param("company_id")int companyId);

    @Modifying
    @Query(value="update ask set remaining_qty = ?4 where user_id = ?1 and company_id = ?2 and placed_time = ?3;", nativeQuery=true)
    public void updateAsk(int userId, int companyId, LocalDateTime dateTime, int qty);
}
