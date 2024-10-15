package com.scratchgame;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Probabilities {
    private List<StandardSymbol> standardSymbols;
    private Map<String, Integer> bonusSymbols;
}