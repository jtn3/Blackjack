package com.example.blackjack;
import java.util.Deque;
import java.util.Stack;

//
//Deck class will hold all cards and distribute to players. Intermediate between api call and player's hands.
//
public class Deck {
    private String deckURL = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
    public Stack <String> cardDeckCodes;
    public Stack <String> cardDeckValues;
    public Deck() {
        cardDeckCodes = new Stack<>();
        cardDeckValues = new Stack<>();
    }
    //
    // returns code from stack. Gives to player.
    //
    public String popCardDeckCodes() {
        String code = cardDeckCodes.pop();
        return code;
    }
    //
    //return value from stack Values. must be called with popCardDeckCodes.
    public String popCardDeckValues() {
        String value = cardDeckValues.pop();
        return value;
    }
    //
    //code and values are pushed together.
    public void pushStacks(String code, String value) {
        cardDeckCodes.push(code);
        cardDeckValues.push(value);
    }

    public void drawSharedDeck(Player player) {
        player.code.add(cardDeckCodes.pop());
        player.hand.add(cardDeckValues.pop());
        return;
    }

    public void clearStacks() {
        cardDeckCodes.clear();
        cardDeckValues.clear();
    }

}
