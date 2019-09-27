package com.example.tashm.tilt;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

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
public class EspressoTest {

    private Button nLevel;
    //sets the rule for the main activity, using the mainactivity for the test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    //tests whether the intent actually works and it takes you to the other activity
    @Test
    public void changeActivity_newActivity(){
        onView(withId(R.id.options)).perform(click());
        onView(withId(R.id.activity_options_window)).check(matches(isDisplayed()));
    }

    //tests intent for newgame
    @Test
    public void changeActivity_toLevel(){
        onView(withId(R.id.complexList)).perform(click());
        onView(withId(R.id.activity_level_selection)).check(matches(isDisplayed()));
    }



}
