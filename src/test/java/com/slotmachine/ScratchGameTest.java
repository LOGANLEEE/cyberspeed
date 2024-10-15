package com.slotmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScratchGameTest {
  private ScratchGame game;
  private final int betAmount = 100;
  private JSONObject config;

  @BeforeEach
  public void setup() {
    // Load test configuration file
    config = new ConfigLoader("config.json").getConfig();
    game = new ScratchGame(config, betAmount);
  }

  @Test
  public void testMatrixGeneration() {
    game.play();
    List<List<Symbol>> matrix = game.getGrid();

    // Verify that the matrix is generated correctly with size 3x3
    assertEquals(3, matrix.size());
    for (List<Symbol> row : matrix) {
      assertEquals(3, row.size());
    }
  }

  @Test
  public void testWinningCombinations() {
    game.play();
    Map<String, List<String>> winningCombinations = game.checkWinningCombinations();

    // Assuming that the test config matrix has known values and combinations
    assertTrue(winningCombinations.containsKey("A"));
    assertEquals(2, winningCombinations.get("A").size()); // Assuming "A" has two winning combinations
  }

  @Test
  public void testRewardCalculation() {
    game.play();
    int reward = game.calculateReward();

    // Assuming specific winning combinations and a known reward
    assertEquals(6600, reward); // Example reward based on the given formula in the problem description
  }

  @Test
  public void testBonusApplication() {
    game.play();
    int reward = game.calculateReward();

    // Check if a bonus symbol was applied
    List<List<Symbol>> matrix = game.getGrid();
    boolean hasBonus = matrix.stream()
        .flatMap(List::stream)
        .anyMatch(symbol -> symbol.getType().equals("bonus"));

    // If bonus exists, reward should reflect bonus addition
    if (hasBonus) {
      assertTrue(reward > betAmount); // Ensure reward has been modified due to the bonus
    }
  }
}