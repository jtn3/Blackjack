package com.example.blackjack;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity{
    int temp1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_table);


        Button StayButton = findViewById(R.id.Stay);
        StayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp1 += 1;
            }
        });

        Button DrawButton = findViewById(R.id.Draw);
        DrawButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                TextView tempText = findViewById(R.id.temp);
               tempText.setText(String.valueOf(temp1));

            }
        });
    }
}
