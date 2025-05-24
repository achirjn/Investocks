package com.investocks.entities;

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
public class ShareBalance {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public ShareBalance(Company company, int quantity, User user) {
        this.company = company;
        this.quantity = quantity;
        this.user = user;
    }
}
