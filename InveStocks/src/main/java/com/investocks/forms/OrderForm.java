package com.investocks.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderForm {
    @NotBlank(message="*required")
    private String companyName;
    @NotBlank(message="*required")
    private String action;
    @NotBlank(message="*required")
    private int price;
    @NotBlank(message="*required")
    private int quantity;
}
