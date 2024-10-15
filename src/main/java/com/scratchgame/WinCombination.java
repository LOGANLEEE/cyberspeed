package com.scratchgame;

import java.util.List;

import lombok.Data;

@Data
public class WinCombination {
    private double rewardMultiplier;
    private String when;
    private int count;
    private String group;
    private List<List<String>> coveredAreas;
}