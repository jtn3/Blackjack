package com.example.blackjack;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class CardActivity extends AppCompatActivity{
    private static CardActivity instance;
    Player User = new Player();
    Player House = new Player();
    Deck sharedDeck = new Deck();
    RequestQueue requestQueue;
    String deckid = "";
    String draw1card = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_table);

        requestQueue = Volley.newRequestQueue(this);
        User.cardSlots = new ImageView[] {findViewById(R.id.imageView1), findViewById(R.id.imageView2) ,findViewById(R.id.imageView3), findViewById(R.id.imageView4) ,findViewById(R.id.imageView5),
                findViewById(R.id.imageView6),findViewById(R.id.imageView7), findViewById(R.id.imageView8),findViewById(R.id.imageView9), findViewById(R.id.imageView10),
                findViewById(R.id.imageView11) };

        House.cardSlots = new ImageView[] { findViewById(R.id.imageView12) ,findViewById(R.id.imageView13), findViewById(R.id.imageView14) ,findViewById(R.id.imageView15),
                findViewById(R.id.imageView16),findViewById(R.id.imageView17), findViewById(R.id.imageView18),findViewById(R.id.imageView19), findViewById(R.id.imageView20),
                findViewById(R.id.imageView21),findViewById(R.id.imageView22) };

        User.invisibility(); House.invisibility(); //ALL card slots are invisible

        //Gives the popup access to restartAll function
        instance = this;
        String deckURL = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";

        //Gets a new deck and draws 2 cards for both players
        getNewDeck(deckURL, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                System.out.println(error);
            }
            @Override
            public void onResponse(String response) {
                deckid = Parse(response, "deck_id");
                String draw30cards = "https://deckofcardsapi.com/api/deck/" + deckid + "/draw/?count=30";
                //
                // Draw 30 cards into stacks, both players draw 2 cards.
                //
                drawCard(draw30cards, new VolleyResponseListener() {
                    @Override
                    public void onError(String error) { }
                    @Override
                    public void onResponse(String response) {
                        sharedDeck.drawSharedDeck(User);   sharedDeck.drawSharedDeck(User);
                        sharedDeck.drawSharedDeck(House);   sharedDeck.drawSharedDeck(House);

                        TextView tempText = findViewById(R.id.textView3);
                        tempText.setText("");

                        User.setValues(User.hand);
                        setCards(User);
                        House.setValues(House.hand);
                        setCards(House);
                        House.cardSlots[1].setImageResource(R.drawable.gray_back);
                    }
                });

                    }
        });
                //Stay and Draw Buttons. Both can call popUp class.
        Button StayButton = findViewById(R.id.Stay);
        StayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User.setValues(User.hand);
                TextView tempText = findViewById(R.id.textView3);
                String holder = "";
                setCards(House);
                stayChecker();
                setCards(House);
                if (House.Busted()) {
                    holder += " House busted! You Won!";
                    popupMaker(holder,v);

                    return;
                }
                if (User.isWinner(House)) {
                    holder += "You Won!";
                    popupMaker(holder,v);
                    return;
                } else {
                    holder += "You Lost!";
                    popupMaker(holder,v);
                    return;
                }
                //CardActivity.this.recreate();
             //   resetAll();
            }
        });

        Button DrawButton = findViewById(R.id.Draw);
        DrawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tempText = findViewById(R.id.textView3);
                sharedDeck.drawSharedDeck(User);
                        if (!(User.setValues(User.hand))) {
                            setCards(House);
                            String holder = "You Busted.";
                            popupMaker(holder,v);
                           // tempText.setText(holder);
                            setCards(User);
                            //User.reset(); House.reset();
                            return;
                        }
                        setCards(User);
            }
        });
    }


    public interface VolleyResponseListener {
        void onError(String error);
        void onResponse(String response);
    }


    // getNewDeck gets a new deckid
    // calls parse function
    //
    public void getNewDeck(String url, final VolleyResponseListener volleyResponseListener) {
        StringRequest jsonStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    String hold;
                    @Override
                    public void onResponse(String response) {
                        try {
                             hold = response;
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        volleyResponseListener.onResponse(hold);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        volleyResponseListener.onError("failure");
                    }
                });
        requestQueue.add(jsonStringRequest);
    }
    //
    //Draws cards and places them into shared deck
    //
    public void drawCard(String url, final VolleyResponseListener volleyResponseListener) {
        StringRequest jsonStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parse2(response);

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        volleyResponseListener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.onResponse("failure2");
                    }
                });
        requestQueue.add(jsonStringRequest);
    }
    // Parse
    // Transform response into JSONObject and then parses.
    // response and key are used to parse.
    private String Parse(String response, String key) {
        String returnString;
        try {
            JSONObject JSONresponse = new JSONObject(response);
                 returnString = JSONresponse.getString(key);
               // return reference;
        }catch (Exception e) {
            System.out.println(e);
            returnString = "Failure2";
        }
        return returnString;
    }
    //
    // Transform response into JsonArray, then parses. Places responses into sharedDeck. Called by drawCard
    //
    private boolean parse2(String response) {
        try {
            JSONObject JSONresponse = new JSONObject(response);
            JSONArray Jsonarray = JSONresponse.getJSONArray("cards");
            for (int i = 0; i < Jsonarray.length(); i++) {
                JSONObject temp = Jsonarray.getJSONObject(i);
                sharedDeck.cardDeckValues.push(temp.getString("value"));
                sharedDeck.cardDeckCodes.push(temp.getString("code"));

            }
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    //displays card images after on screen
    public void setCards(Player player) {
        for (int i = 0; i < player.code.size();i++) {
            Resources res = this.getResources();
            String temp = "" +player.code.get(i).charAt(1) + player.code.get(i).charAt(0); //+ ".png";
            temp = temp.toLowerCase();
            System.out.println(temp);
            int resID = res.getIdentifier(temp , "drawable", CardActivity.this.getPackageName());
            player.cardSlots[i].setImageResource(resID);
            player.cardSlots[i].setVisibility(View.VISIBLE);
        }
    }
    // Makes the value1 of the House greater than 17. Does not check Busted or not.
    public void stayChecker() {
        if (House.value2 < 17 && House.value1 < 17) {
            sharedDeck.drawSharedDeck(House);
            setCards(House);
            House.setValues(House.hand);
            if (House.value2 < 17 && House.value1 < 17) {
                stayChecker();
            }
            return;
        }
    }
        // allows outside classes to use my activity method. Recreate() in popup.java
        public static CardActivity getInstance() {
            return instance;
        }
        //Allows the creation of popup windows when called.
        public void popupMaker(String result, View v) {
            popUp resultView = new popUp();
            resultView.popupText = result;
            resultView.showPopupWindow(v);
          //  CardActivity.getInstance().recreate();
        }
    //
    //
    //resets everything, player and shareddeck. Restarts blackjack game
    //
    public void resetAll() {
        sharedDeck.clearStacks();
        User.reset(); House.reset();
        String reshuffleDeck = "https://deckofcardsapi.com/api/deck/"+ deckid +"/shuffle/";
        getNewDeck(reshuffleDeck, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                System.out.println(error);
            }
            @Override
            public void onResponse(String response) {
                //deckid = Parse(response, "deck_id");
                String draw30cards = "https://deckofcardsapi.com/api/deck/" + deckid + "/draw/?count=30";
                //
                // Draw 30 cards into stacks, both players draw 2 cards.
                //
                drawCard(draw30cards, new VolleyResponseListener() {
                    @Override
                    public void onError(String error) { }
                    @Override
                    public void onResponse(String response) {
                        sharedDeck.drawSharedDeck(User);   sharedDeck.drawSharedDeck(User);
                        sharedDeck.drawSharedDeck(House);   sharedDeck.drawSharedDeck(House);

                        TextView tempText = findViewById(R.id.textView3);
                        tempText.setText("");

                        User.setValues(User.hand);
                        setCards(User);
                        House.setValues(House.hand);
                        setCards(House);
                        House.cardSlots[1].setImageResource(R.drawable.gray_back);
                    }
                });

            }
        });

    }
}






