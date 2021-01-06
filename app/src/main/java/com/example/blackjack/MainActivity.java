package com.example.blackjack;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import com.google.gson.Gson;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
//import com.google.gson.
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Button changes user interfaces from start
//  into the Blackjack game.

        Button PlayButton = findViewById(R.id.playButton);
        PlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CardActivity.class ));
            }
        });
    }
}
/*
//public static JSON
public class MainActivity {
    public static void main(String[] args) {
        try {
            URL apiURL = new URL("https://deckofcardsapi.com/api/deck/new/");
            URLConnection connect = apiURL.openConnection();
            //connect.setRequestProperty(,);
            InputStream response = connect.getInputStream();

        } catch(Exception e) {
            System.out.println(e);
            System.out.println("Internet connection failed");
        }
        String deckid;
        Player me = new Player("House");

        System.out.println(me.name);
    }
}
*/