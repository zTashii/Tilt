package com.example.tashm.tilt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class OptionsWindow extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        gameMusic.release();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_options_window);
        //function to initialise the seekbar for music
        initialiseVolume();
    }

    //intent to call how to play
    public void callHowToPlay(View v){
        Intent intentHowToPlay = new Intent(this, HowToPlay.class);
        startActivity(intentHowToPlay);
    }

    //intent to go back to previous activity.
    public void onDoneClick(View v){
        this.finish();
    }

}
