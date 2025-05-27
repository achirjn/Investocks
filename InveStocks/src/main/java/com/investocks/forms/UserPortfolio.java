package com.investocks.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolio {

    private String companyName;
    private int shareBalance;
    private float currentPrice;
    private float profit;

}
