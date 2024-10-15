package com.slotmachine;

import org.json.JSONObject;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the betting amount: ");
        int betAmount = scanner.nextInt();
        
        ConfigLoader configLoader = new ConfigLoader("config.json");
        ScratchGame game = new ScratchGame(configLoader.getConfig(), betAmount);
        
        game.play();
        
        scanner.close();
    }
}