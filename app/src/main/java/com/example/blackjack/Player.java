package com.example.blackjack;


import java.util.*;

// User and "House",
public class Player {
    String name;
    ArrayList<String> hand;
    int value1;
    int value2;
    public Player(String name) {
        this.name = name;
    }

    void reset() {
        value1 = 0; value2 = 0; hand.clear();
    }
}
