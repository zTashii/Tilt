package com.example.tashm.tilt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer gameMusic;
    SharedPreferences sharedPreferences;
    private SeekBar curVolume = null;
    private AudioManager audioManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        gameMusic = MediaPlayer.create(this, R.raw.energy);
//        gameMusic.start();
        //initialise new game button. when the application loads, it tests for whether the integer in shared preferences is
        //a certain number, if it is, then the button text becomes "continue"
        Button newGameButton = (Button) findViewById(R.id.newGame);
        sharedPreferences = getSharedPreferences("saveGamePref", Activity.MODE_PRIVATE);
        int newGameClicked = sharedPreferences.getInt("newGameClicked", -1);
        if(newGameClicked == 1){
            newGameButton.setText("Continue");
        } else{
            newGameButton.setText("New Game");
        }
    }

//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        gameMusic.stop();
//        gameMusic.release();
//    }

    //Intent to go to another activity
    public void goOption(View v){
        Intent intentOption = new Intent(this, OptionsWindow.class);
        finish();
        startActivity(intentOption);
    }

    //intent to go to another activity but also put an integer into shared preferences, this will
    //put "1" into the newGameClicked string into the shared preference and apply it.
    public void goGame(View v){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("newGameClicked", 1);
        editor.apply();
        Intent intentGame = new Intent(this, GameWindow.class);
        startActivity(intentGame);
    }

    //intent to go to the level selection screen
    public void goLevelSelector(View v){
        Intent intentLevelSelector = new Intent(this, LevelSelectionWindow.class);
        finish();
        startActivity(intentLevelSelector);
    }

    //returns to the previous activity when called
    public void onDoneClick(View v){
        finish();
    }

    //creates the function for the music to be controlled by the seekbar(volume bar)
    //this function gets called in the options window class because that is where the seekbar is
    public void initialiseVolume(){
        try {
            //gets the seekbar
            curVolume = (SeekBar) findViewById(R.id.seekBar);
            //implements audiomanager
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            //sets the seekbar to be linked with the devices volume settings
            curVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            curVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            //seekback change listener to check if the seekbar has been changed. if so then it will
            //set the volume to the progression of the seekbar.
            curVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
