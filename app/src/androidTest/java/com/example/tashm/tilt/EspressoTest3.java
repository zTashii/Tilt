package com.example.tashm.tilt;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by tashm on 13/12/2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class EspressoTest3 {

    //sets the rule for the main activity, using the mainactivity for the test
    @Rule
    public ActivityTestRule<GameWindow> activityTestRule = new ActivityTestRule<>(
            GameWindow.class);

    //tests whether the intent actually works and it takes you to the other activity
    @Test
    public void changeActivity_newActivity(){
        onView(withId(R.id.retryButton)).perform(click());
        onView(withId(R.id.activity_game_window)).check(matches(isDisplayed()));
    }



}
