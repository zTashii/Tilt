package com.example.tashm.tilt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
This activity is for the How to play activity which essentially is just here
to display information on how to play.
To access this, the user would need to navigate to the options menu.
 */
public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        /*
        Display Metrics is used to control the appearance of the activity.
        I use this to make the activity smaller so it can act as a pop up over the
        Options menu activity.
         */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        //Sets the window of the activity a fraction smaller than it normally is.
        //So the options window is viewable in the background.
        getWindow().setLayout((int)(width*.8),(int)(height*.7));


        //Declares the 2 main items i need to use to make the How to play activity function.
        final TextView textView = (TextView) findViewById(R.id.hptText);
        final Button button = (Button) findViewById(R.id.nextButton);
        //i set an on click listener so when the "next" button is clicked, it will display
        //next set of strings that i have set in the Strings.xml file to tell users how to play the game.
        //I do this multiple times so that all of the strings are displayed.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(getResources().getString(R.string.htp2));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText(getResources().getString(R.string.htpTilt));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textView.setText(getResources().getString(R.string.htpTouch));
                                button.setOnClickListener(new View.OnClickListener() {
                                    //when it reaches this point. It will set the next button to be disabled
                                    //so that it is not clickable. And therefore shows that there is no more information
                                    //to display.
                                    @Override
                                    public void onClick(View v) {
                                        textView.setText(getResources().getString(R.string.htpPlay));
                                        button.setEnabled(false);
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

    }
}