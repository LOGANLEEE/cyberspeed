package com.scratchgame;

import lombok.Data;

@Data
public class Symbol {
    private String type;
    private double rewardMultiplier;
    private String impact;
    private int extra;
}