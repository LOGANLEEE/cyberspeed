package com.slotmachine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Symbol {
    private String symbol;
    private double rewardMultiplier;
    private String type;
}