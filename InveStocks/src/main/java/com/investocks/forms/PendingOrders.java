package com.investocks.forms;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingOrders {
    private String companyName;
    private String action;
    private float price;
    private int quantity;
    private LocalDateTime placedTime;
}
