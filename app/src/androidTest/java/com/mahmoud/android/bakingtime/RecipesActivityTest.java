package com.mahmoud.android.bakingtime;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.mahmoud.android.bakingtime.ui.activity.RecipesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    @Rule public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void openActivity_LoadResipesList(){
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + 5000;
        while (System.currentTimeMillis() < endTime);

        if (getRVCount() > 0) {
            onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));

            onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_short_description_text_view)).check(matches(withText(containsString("Recipe Introduction"))));
        }
    }

    private int getRVCount(){
        RecyclerView recyclerView = (RecyclerView) mActivityTestRule.getActivity().findViewById(R.id.rv_recipes);
        return recyclerView.getAdapter().getItemCount();
    }

}
