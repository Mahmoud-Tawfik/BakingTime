package com.mahmoud.android.bakingtime.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.ui.fragment.RecipesFragment;

/**
 * Created by Mahmoud on 11/13/17.
 */

public class StepsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipesFragment recipesFragment = new RecipesFragment();
        fragmentManager.beginTransaction()
                .add(R.id.steps_container, recipesFragment)
                .commit();


    }

}
