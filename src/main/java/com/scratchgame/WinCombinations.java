package com.scratchgame;

import lombok.Data;
import java.util.Map;

@Data
public class WinCombinations {
    private Map<String, WinCombination> winCombinations;
}