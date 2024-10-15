package com.scratchgame;

import lombok.Data;
import java.util.Map;

@Data
public class GameConfig {
    private int columns;
    private int rows;
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;
    private WinCombinations winCombinations;
}