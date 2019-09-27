package com.example.tashm.tilt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameWindow extends MainActivity implements SensorEventListener {
    //https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
    //http://stackoverflow.com/questions/6479637/android-accelerometer-moving-ball

    public static Integer[] levelScore= {1,1,1,0,0,0,0,0,0,0};
    public static SensorManager sensorManager;
    public static int numOfLevels = 10;
    ViewPager viewPager;
    PageFragmentPagerAdapter pageFragmentPagerAdapter;
    int position = 0;
    TextView xText, yText, zText;
    ImageView ball, level1, level2, level3, level3b, level3c, referenceBall, finishBall;
    Button pauseButton, retryButton;
    TextView score;
    TextView startGame;
    private Sensor accelerometer;
    private int xPos, yPos;
    private int xMax, yMax;
    private int gBallX, gBallY, nBallX, nBallY, rBallX, rBallY;
    private int xSpeed = 5;
    private int ySpeed = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        gameMusic.release();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_window);
        //declarations for everything i may need
        ball = (ImageView) findViewById(R.id.ball);
        referenceBall = (ImageView) findViewById(R.id.referenceBall);
        finishBall = (ImageView) findViewById(R.id.finishBall);
        level1 = (ImageView) findViewById(R.id.level1Selector);
        level2 = (ImageView) findViewById(R.id.level2Selector);
        level3 = (ImageView) findViewById(R.id.level3Selector);
        level3b = (ImageView) findViewById(R.id.level3bSelector);
        level3c = (ImageView) findViewById(R.id.level3cSelector);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        retryButton = (Button) findViewById(R.id.retryButton);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        score = (TextView) findViewById(R.id.scoreString);
//        xText = (TextView) findViewById(R.id.xText);
//        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);
        startGame = (TextView) findViewById(R.id.startGame);

        /*
        Gets an integer from another activity.
        i use this to get a number, which would be between 0 and the number
        of current levels implemented in the game, from the level select activity
        where it would send say a number 4, and the view pager will navigate to page number 4.
        This purpose is to open a specific page on the view pager from another activity
         */

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("viewpager_position");
        }

        //Initialise the ViewPager for the current activity;
        pageFragmentPagerAdapter = new PageFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new customOnPageChangeListener());
        viewPager.getCurrentItem();

        /*
        This code sets the page of the viewPager depending on what the position
        integer is sent from the level selection activity
         */
        viewPager.setCurrentItem(position);
        setScore(position);

        //Initialise the accelerometer for the current activity
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //Pause the game and also opens the option menu.
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAccelerometer();
                Intent intentOption = new Intent(GameWindow.this, OptionsWindow.class);
                startActivity(intentOption);
            }
        });
        saveLevelScores();
        onStart();
    }

    public void saveLevelScores(){
        sharedPreferences = getSharedPreferences("saveGamePref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("level1Complete", 0);
        editor.putInt("level2Complete", 0);
        editor.putInt("level3Complete", 0);
        editor.putInt("level4Complete", 0);
        editor.putInt("level5Complete", 0);
        editor.putInt("level6Complete", 0);
        editor.putInt("level7Complete", 0);
        editor.putInt("level8Complete", 0);
        editor.putInt("level9Complete", 0);
        editor.putInt("level10Complete", 0);
        editor.apply();
    }

    @Override
    protected void onStart(){
        super.onStart();
        setupLevel();

    }

    @Override
    public void onPause(){
        super.onPause();
        stopAccelerometer();
    }


    @Override
    public void onResume() {
        super.onResume();
        startAccelerometer();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //gets x and y of the start ball, finish ball and the game ball
        gBallX = (int) ball.getX();
        gBallY = (int) ball.getY();
        nBallX = (int) finishBall.getX();
        nBallY = (int) finishBall.getY();
        rBallX = (int) referenceBall.getX();
        rBallY = (int) referenceBall.getY();

        //The code below is just for debugging purposes.
        //To view the values of the sensor on each axis.
        //i only needed to view the x and y as it is in 2d space
        //This was just to see when the accelerometer is on or off.

//        xText.setText("endBallX: " + nBallX);
//        yText.setText("RedBallX: " + gBallX + "RedBallY: " + gBallY + "\nfinBallX: " + rBallX + "\nfinBallY: " + rBallY);

        zText.setText("Mode: Playing");

        //Sets the data to "coords" from the accelerometer
        //also adds the speed of the accelerometer. So it appears that the
        //accelerometer is making the ball move fast
        xPos -= (int) sensorEvent.values[0]*xSpeed;
        yPos += (int) sensorEvent.values[1]*ySpeed;

        updateBall();
    }

    public void refresh(){
        onRestart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(GameWindow.this, GameWindow.class);
        finish();
        startActivity(i);
    }

    public void setupLevel(){
        ball.setX(rBallX);
        ball.setY(rBallY);
        startAccelerometer();

    }

    public void restartLevel(View v){
        refresh();
        startAccelerometer();

    }
    public void updateBall(){

        //sets the bounds of the screen.
        //gets the width and height of the screen and offsets the boundaries by 45 units so that the ball
        //is in view of the screen instead of being stopped outside of the view
        xMax = (viewPager.getWidth()/2)-45;
        yMax = (viewPager.getHeight()/2)-45;

        //sets the position of the imageview to the position of the accelerometer
        ball.setTranslationX(xPos);
        ball.setTranslationY(yPos);

        //sets the boundaries of the ball to stay within the boundaries of the screen.

        if(xPos > xMax){
            xPos = xMax;
        }else if(xPos < -xMax){
            xPos = -xMax;
        }
        if(yPos > yMax){
            yPos = yMax;
        }else if(yPos < -yMax) {
            yPos = -yMax;
        }
        //sets the condition for the first level of the game. if the ball is at this point, then game wins or loses
        //depending on which point the ball is at.
        if((gBallX == 495) && (gBallY <=735)){
            startGame.setText("Well done \nTap here to go to next level");
            stopAccelerometer();
            startGame.setVisibility(View.VISIBLE);
            //onclick to check for whether the user progresses to the next level or retries the current level
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //shared preference to set the score for the 1st level if it has been completed
                    viewPager.setCurrentItem(position+1);
                    sharedPreferences = getSharedPreferences("saveGamePref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("level1Score", 1);
                    editor.apply();
                    setScore(position);
                }
            });
        }
        if(gBallX != 495){
            startGame.setText("Game Over");
            startGame.setVisibility(View.VISIBLE);
            stopAccelerometer();
        }
        if(gBallY > rBallY){
            startGame.setText("Game Over");
            startGame.setVisibility(View.VISIBLE);
            stopAccelerometer();
        }

        ball.invalidate();

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not going to implement this
    }

    //small function to quickly start the accelerometer with registerListener
    //Easier than constantly typing sensorManager.regist....
    public void startAccelerometer(){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        zText.setText("Mode: Playing");
    }

    //small function to quickly stop the accelerometer with unregisterListener
    //Easier than constantly typing sensorManager.unregist....
    public void stopAccelerometer(){
        sensorManager.unregisterListener(this);
        zText.setText("Mode: Not Playing");
    }

    //Sets the completion of the level by editing a text view programmatically
    //Checks the position of the page and depending on what the position is it will say if it
    //has been completed or not
    public void setScore(int position){
        sharedPreferences = getSharedPreferences("saveGamePref", Activity.MODE_PRIVATE);
        int level1Score = sharedPreferences.getInt("level1Score", -1);
        int level2Score = sharedPreferences.getInt("level2Score", -1);
        int level3Score = sharedPreferences.getInt("level3Score", -1);
        int level4Score = sharedPreferences.getInt("level4Score", -1);
        int level5Score = sharedPreferences.getInt("level5Score", -1);
        int level6Score = sharedPreferences.getInt("level6Score", -1);
        int level7Score = sharedPreferences.getInt("level7Score", -1);
        int level8Score = sharedPreferences.getInt("level8Score", -1);
        int level9Score = sharedPreferences.getInt("level9Score", -1);
        int level10Score = sharedPreferences.getInt("level10Score", -1);
//        score.setText("Completed " + (String.valueOf(levelScore[position])));
        switch(position){
            case 0:
                score.setText("Completed: " + level1Score);
            case 1:
                score.setText("Completed: " + level2Score);
            case 2:
                score.setText("Completed: " + level3Score);
            case 3:
                score.setText("Completed: " + level4Score);
            case 4:
                score.setText("Completed: " + level5Score);
            case 5:
                score.setText("Completed: " + level6Score);
            case 6:
                score.setText("Completed: " + level7Score);
            case 7:
                score.setText("Completed: " + level8Score);
            case 8:
                score.setText("Completed: " + level9Score);
            case 9:
                score.setText("Completed: " + level10Score);
        }
    }

    public void onDoneClick(View v) {
        finish();
    }


    /*
    ViewPager adapter to create multiple pages/levels.
    Used guidance from:
    https://github.com/covcom/300CEM/tree/master/Week_08_Graphics_and_animation
    https://github.com/codepath/android_guides/wiki/ViewPager-with-FragmentPagerAdapter
     */
    private class PageFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public PageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //currently, only 3 levels have been implemented and for the sake of demonstration, i am only implementing the functionality of the first level
            //if the previous levels score is 1, then it will display the next level, if not, display "comingsoon" fragment
            sharedPreferences = getSharedPreferences("saveGamePref", Activity.MODE_PRIVATE);
            int level1Score = sharedPreferences.getInt("level1Score", -1);
            int level2Score = sharedPreferences.getInt("level2Score", -1);
            switch(position){
                case 0:
                    return Level1Fragment.create(position,"Level " + (position + 1));
                case 1:
                    if(level1Score ==1){
                        return Level2Fragment.create(position,"Level " + (position + 1));
                    }else{
                        return ComingSoonFragment.create(position,"Level " + (position + 1));
                    }
                case 2:
                    if(level2Score ==1){
                        return Level3Fragment.create(position,"Level " + (position + 1));
                    }else{
                        return ComingSoonFragment.create(position,"Level " + (position + 1));
                    }
                case 3:case 4:case 5:case 6:case 7:case 8:case 9:
                    return ComingSoonFragment.create(position,"Level " + (position + 1));
                default:
                    return Level1Fragment.create(position,"Level " + (position + 1));
            }
        }

        //sets the number of pages/levels the game has
        @Override
        public int getCount() {
            return numOfLevels;
        }

        //sets the page title to the position of the page +1 so that the 1st page isnt set at 0
        @Override
        public CharSequence getPageTitle(int position){
            return "Level " + (position + 1);
        }
    }

    //Page change listener to check for page changes
    private class customOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            startGame.setVisibility(View.GONE);
//            refresh();
        }

        @Override
        public void onPageSelected(int position) {
            //sets the score of the current level from the array of scores
            setScore(position);

            //retrieves data from shared preference to test for the next level
            sharedPreferences = getSharedPreferences("saveGamePref", Activity.MODE_PRIVATE);
            int level1Score = sharedPreferences.getInt("level1Score", -1);
            if (level1Score !=1) {
                ball.setVisibility(View.GONE);
                stopAccelerometer();
            } else {
                ball.setVisibility(View.VISIBLE);
                startAccelerometer();
            }
            switch(position){
                case 0:
                    //sets the start and finish for the 1st level
                    finishBall.setVisibility(View.INVISIBLE);
                    referenceBall.setVisibility(View.INVISIBLE);
                    return;
                case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:
                    ball.setVisibility(View.GONE);
                    finishBall.setVisibility(View.GONE);
                    referenceBall.setVisibility(View.GONE);
                    startGame.setVisibility(View.GONE);
                    //set new start and finish, not yet fully implemented
                    return;
                default:
                    return;

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

}



