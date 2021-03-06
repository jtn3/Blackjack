package com.example.blackjack;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class popUp {
    String popupText;

    //PopupWindow display method

    public void showPopupWindow(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 300);

        //Initialize the elements of our window, install the handler

        TextView test2 = popupView.findViewById(R.id.textView4);
        test2.setText(popupText);

        Button buttonEdit = popupView.findViewById(R.id.button);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardActivity.getInstance().resetAll();
                popupWindow.dismiss();

                //   CardActivity.getInstance().recreate();

            }
        });


        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

               // Does nothing when clicked outside
                //popupWindow.dismiss();
                return true;
            }
        });
    }

}