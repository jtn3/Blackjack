package com.example.blackjack;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


//
//Intro Activity, leads to cardActivity
//
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
