package com.investocks.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ask {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int price;
    private int quantity;
    private int remainingQty;
    private LocalDateTime placedTime;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy="ask")
    @SuppressWarnings("unused")
    private List<Trade> trade;

    public Ask() {
    }

    public Ask(Company company, int price, int quantity, User user) {
        this.company = company;
        this.placedTime = LocalDateTime.now();
        this.price = price;
        this.quantity = quantity;
        this.remainingQty = quantity;
        this.user = user;
    }

    public Ask(Company company, LocalDateTime placedTime, int price, int quantity, int remainingQty, User user) {
        this.company = company;
        this.placedTime = placedTime;
        this.price = price;
        this.quantity = quantity;
        this.remainingQty = remainingQty;
        this.user = user;
    }


    public void setPlacedTime(){
        placedTime = LocalDateTime.now();
    }
}
