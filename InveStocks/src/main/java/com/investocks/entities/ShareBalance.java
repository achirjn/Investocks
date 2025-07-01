package com.investocks.entities;

import com.investocks.entities.compositeKeys.ShareBalanceCK;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(ShareBalanceCK.class)// for 2nd method(ck creation), no need for @IdClass, and inplace of @Id use @EmbeddedId. Also use @Embeddable over the ck class
public class ShareBalance {

    private int quantity;
    private float amountSpent=0;

    @Id
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
