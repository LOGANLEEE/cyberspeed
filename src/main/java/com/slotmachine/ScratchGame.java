package com.slotmachine;

import org.json.JSONObject;

import java.util.*;

public class ScratchGame {
    private final JSONObject config;
    private final int betAmount;
    private final List<List<Symbol>> grid;

    public ScratchGame(JSONObject config, int betAmount) {
        this.config = config;
        this.betAmount = betAmount;
        this.grid = new ArrayList<>();
    }

    public void play() {
        generateMatrix();
        displayMatrix();

        int reward = calculateReward();
        System.out.println("Total Reward: " + reward);
    }

    private void generateMatrix() {
        // Generate a 3x3 matrix based on symbol probabilities
        JSONObject symbols = config.getJSONObject("symbols");
        JSONObject probabilities = config.getJSONObject("probabilities").getJSONArray("standard_symbols")
                .getJSONObject(0).getJSONObject("symbols");

        for (int i = 0; i < 3; i++) {
            List<Symbol> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Symbol symbol = getRandomSymbol(symbols, probabilities);
                row.add(symbol);
            }
            grid.add(row);
        }
    }

    private Symbol getRandomSymbol(JSONObject symbols, JSONObject probabilities) {
        // Implement logic to get a random symbol based on probabilities
        int totalProbability = probabilities.toMap().values().stream()
                .mapToInt(prob -> (int) prob) // Explicitly cast to int
                .sum();

        int random = new Random().nextInt(totalProbability) + 1;

        for (String key : probabilities.keySet()) {
            int prob = probabilities.getInt(key);
            if (random <= prob) {
                JSONObject symbolData = symbols.getJSONObject(key);
                return new Symbol(key, symbolData.getDouble("reward_multiplier"), symbolData.getString("type"));
            }
            random -= prob;
        }
        return null;
    }

    private void displayMatrix() {
        System.out.println("Generated Matrix:");
        for (List<Symbol> row : grid) {
            for (Symbol symbol : row) {
                System.out.print(symbol.getSymbol() + " ");
            }
            System.out.println();
        }
    }

    protected int calculateReward() {
        int reward = 0;

        // Step 1: Check for winning combinations
        Map<String, List<String>> winningCombinations = checkWinningCombinations();

        // Step 2: Calculate rewards for symbols involved in winning combinations
        for (String symbolKey : winningCombinations.keySet()) {
            Symbol symbol = getSymbol(symbolKey);
            int symbolReward = (int) (betAmount * symbol.getRewardMultiplier());

            // Apply multipliers for each winning combination
            for (String combination : winningCombinations.get(symbolKey)) {
                symbolReward *= getCombinationMultiplier(combination);
            }

            // Add this symbol's reward to the total reward
            reward += symbolReward;
        }

        // Step 3: Apply any bonus symbols
        reward = applyBonusSymbols(reward);

        return reward;
    }

    protected Map<String, List<String>> checkWinningCombinations() {
        // Implement logic to check for winning combinations in the matrix
        Map<String, List<String>> appliedCombinations = new HashMap<>();

        // Example: Check for vertical, horizontal, or diagonal matches (dummy logic, to
        // be implemented)
        // Logic would include checking all rows, columns, and diagonals based on the
        // config

        // Example: Add a winning combination for symbol "A"
        appliedCombinations.put("A", List.of("same_symbol_5_times", "same_symbols_vertically"));

        return appliedCombinations;
    }

    private int getCombinationMultiplier(String combination) {
        // Return the multiplier based on the winning combination
        return switch (combination) {
            case "same_symbol_3_times" -> 1;
            case "same_symbol_5_times" -> 5;
            case "same_symbols_vertically" -> 2;
            case "same_symbols_horizontally" -> 2;
            case "same_symbols_diagonally_left_to_right" -> 5;
            case "same_symbols_diagonally_right_to_left" -> 5;
            default -> 1;
        };
    }

    private Symbol getSymbol(String symbolKey) {
        JSONObject symbols = config.getJSONObject("symbols");
        JSONObject symbolData = symbols.getJSONObject(symbolKey);
        return new Symbol(symbolKey, symbolData.getDouble("reward_multiplier"), symbolData.getString("type"));
    }

    protected List<List<Symbol>> getGrid() {
        return grid;
    }

    private int applyBonusSymbols(int reward) {
        // Apply bonuses if there are winning combinations
        for (List<Symbol> row : grid) {
            for (Symbol symbol : row) {
                if (symbol.getType().equals("bonus")) {
                    reward = applyBonus(symbol, reward);
                }
            }
        }
        return reward;
    }

    private int applyBonus(Symbol symbol, int reward) {
        switch (symbol.getSymbol()) {
            case "10x":
                return reward * 10;
            case "5x":
                return reward * 5;
            case "+1000":
                return reward + 1000;
            case "+500":
                return reward + 500;
            default:
                return reward;
        }
    }

}