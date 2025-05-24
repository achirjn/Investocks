package com.investocks.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int price;
    private LocalDateTime tradeTime;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name="bid_id")
    private Bid bid;
    @ManyToOne
    @JoinColumn(name="ask_id")
    private Ask ask;

    public Trade(Ask ask, Bid bid, Company company, int price, int quantity) {
        this.ask = ask;
        this.bid = bid;
        this.company = company;
        this.price = price;
        this.quantity = quantity;
        this.tradeTime = LocalDateTime.now();
    }

    public void setTradeTime(){
        tradeTime = LocalDateTime.now();
    }
}
