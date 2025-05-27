package com.investocks.entities;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@ToString
@Table(name="company_info")
public class Company{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String profilePic;
    private String name;
    @Column(name="circuit_limit")
    private float circuitLimit;
    @Column(name="closed_price")
    private float closedPrice;
    @Column(name="current_price")
    private float currentPrice;
    private float profit;
    private String sector;

    @OneToMany(mappedBy="company",cascade=CascadeType.ALL)
    private List<Bid> bid;
    @OneToMany(mappedBy="company",cascade=CascadeType.ALL)
    private List<Ask> ask;
    @OneToMany(mappedBy="company",cascade=CascadeType.ALL)
    private List<Trade> trade;
    @OneToMany(mappedBy="company",cascade=CascadeType.ALL)
    private List<ShareBalance> shareBalance;

    public Company(float circuitLimit, float closedPrice, float currentPrice, String name, float profit, String sector) {
        this.circuitLimit = circuitLimit;
        this.closedPrice = closedPrice;
        this.currentPrice = currentPrice;
        this.name = name;
        this.profit = profit;
        this.sector = sector;
    }

    public Company() {
    }

    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public float getCircuitLimit() {
        return circuitLimit;
    }

    public void setCircuitLimit(float circuitLimit) {
        this.circuitLimit = circuitLimit;
    }

    public float getClosedPrice() {
        return closedPrice;
    }

    public void setClosedPrice(float closedPrice) {
        this.closedPrice = closedPrice;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getId() {
        return id;
    }
}
