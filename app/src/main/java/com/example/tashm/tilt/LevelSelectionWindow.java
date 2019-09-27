package com.example.tashm.tilt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

//i use imports to get an integer from another class
import static com.example.tashm.tilt.GameWindow.numOfLevels;
import static com.example.tashm.tilt.GameWindow.levelScore;

public class LevelSelectionWindow extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //music release() allows the music to be played, when its not commented out.
        //the music works but for the sake of demonstration, i enabled it for a brief moment to show that it works
        //and then disabled it so the application can run smoother
//        gameMusic.release();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level_selection_window);

        /*
        The code below is to add buttons dynamically to the activity depending on how many
        levels there are available.
        I also have the code testing for whether the current position in the levelScore[] array
        has an integer less than 80, if so, then it will set the rest of the buttons to not be enabled
        or clickable.
        I also have an on click listener to dynamically check for the button being pressed and
        navigate to the page that the position is on. For example, if you press on Level 7, it will
        take you to page 7 in the viewpager, or page 6 if you start from page 0.
         */
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_level_selection);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
        Button[] nLevel = new Button[numOfLevels];
        //for loop which creates a button depending on how many levels i want integrated
        for (int i = 0; i < numOfLevels; i++) {
            //new button in this activity
            nLevel[i] = new Button(this);
            //sets the text of the button to the number of i +1 every iteration
            nLevel[i].setText("Level " + (i +1));
            //applies the button too the layout
            nLevel[i].setLayoutParams(param);
            layout.addView(nLevel[i]);
            //tests for score, if level[position] is completed, the next button will be available
            if(levelScore[i] < 1 && i != 0){
                nLevel[i].setEnabled(false);
            }
            //onclicklistener to make the buttons actually work
            final int finalI = i;
            nLevel[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent level = new Intent(LevelSelectionWindow.this,GameWindow.class);
                    //Sends data to another activity, data called viewpager_position and
                    //the position i want to send "finalI"
                    level.putExtra("viewpager_position", finalI);
                    startActivity(level);
                }
            });
        }
    }




    public void onDoneClick(View v){
        finish();
    }

}

