package com.example.blackjack;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.*;

// User and "House",
public class Player {
    ArrayList<String> code;
    ArrayList<String> hand;
    ImageView[] cardSlots;
    //ArrayList<ImageView> cardSlots;
    int value1; // holds default hand, ACE is equal to 11. NOTE: only one Ace can be valued at 11 in a players hand.
    int value2; // holds value if ALL Aces must equal 1

    public Player() {
        hand = new ArrayList<String>();
        code = new ArrayList<String>();
      //  cardSlots = new ImageView[11];
       // cardSlots = new ArrayList<ImageView>();
    }

    void reset() {
        value1 = 0; value2 = 0; hand.clear(); code.clear(); this.invisibility();
    }
    //
    // Goes through Arraylist and adds up all cards values. Returns false when both numbers go over 21. Player loses.
    //Called when after Player draws a card.
    public boolean setValues(ArrayList<String> hand) {
        value1 = 0; value2 = 0;
        boolean hasAce = false;
        for(int i = 0; i < hand.size(); i++) {
           if (isNumeric(hand.get(i))) {
               value1 += Integer.parseInt(hand.get(i));   //Handles numeric cards except Ace
               value2 += Integer.parseInt(hand.get(i));
           } else if (hand.get(i).charAt(0) == 'A') {
                if (hasAce == false) {
                    value1 += 11; value2+=1; //Handles Aces
                    hasAce = true;
                    if (this.Busted()) {
                        return false;
                    }
                    continue;
                }
                value1++; value2++;
           } else {
               value1+=10; value2+=10; //Handles face cards
           }
           if (this.Busted()) {
               return false;
           }
        }
        return true;
    }
    //
    //checks which numbers are higher. Does not account for over 21. Check if busted before this. If tied, other wins.
    //
    public boolean isWinner(Player other) {
        int finalValue1; int finalValue2;
        if (this.value1 > this.value2) {
            finalValue1 = this.value1;
        } else {
            finalValue1 = this.value2;
        }

        if(other.value1  >= other.value2) {
            finalValue2 = other.value1;
        } else {
            finalValue2 = other.value2;
        }

        if(finalValue1  >= finalValue2) {
            return true;
        } else {
            return false;
        }
    }

    //
    //Checks if card value stored in ArrayList hand is numeric
    //
    private boolean isNumeric(String card) {
        try {
           int a = Integer.parseInt(card);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    //
    // checks if Player busted after drawing a card
    //
    public boolean Busted() {
        if (value1 > 21 && value2 > 21) {
            return true;
        }
        return false;
    }
    //sets all all images in cardSlots to ivinsible called at start of every new game.
    public void invisibility() {
        for (ImageView i : cardSlots) {
            i.setVisibility(View.INVISIBLE);
        }
    }


}
