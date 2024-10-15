package com.scratchgame;

import lombok.Data;
import java.util.Map;

@Data
public class StandardSymbol {
  private int column;
  private int row;
  private Map<String, Integer> symbols;
}