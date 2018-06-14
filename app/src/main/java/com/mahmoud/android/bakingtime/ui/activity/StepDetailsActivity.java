package com.mahmoud.android.bakingtime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.fragment.IngredientsFragment;
import com.mahmoud.android.bakingtime.ui.fragment.StepDetailsFragment;

import butterknife.BindView;

/**
 * Created by Mahmoud on 1/1/18.
 */

public class StepDetailsActivity extends AppCompatActivity {
    public Recipe recipe;
    public int currentStep;
    private final static String CURRENT_RECIPE = "current_recipe";
    private final static String CURRENT_STEP = "current_step";

    public @BindView(R.id.previous_step) Button previousStep;
    public @BindView(R.id.next_step) Button nextStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(CURRENT_RECIPE)) {
                recipe = intentThatStartedThisActivity.getParcelableExtra(CURRENT_RECIPE);
                currentStep = intentThatStartedThisActivity.getIntExtra(CURRENT_STEP,-1);
            }
        }

        if (savedInstanceState == null){
            addStepDetailsFragment();
        } else {
            recipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
            currentStep = savedInstanceState.getInt(CURRENT_STEP);
            addStepDetailsFragment();
        }
    }

    private void addStepDetailsFragment(){
        if (previousStep != null) previousStep.setVisibility(currentStep == -1 ? View.INVISIBLE : View.VISIBLE);
        if (nextStep != null) nextStep.setVisibility(currentStep == recipe.getSteps().size()-1 ? View.INVISIBLE : View.VISIBLE);

        if (currentStep == -1){
            addIngredientsFragment();
        } else {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.recipe = recipe;
            stepDetailsFragment.currentStep = currentStep;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_RECIPE, recipe);
        outState.putInt(CURRENT_STEP, currentStep);
        super.onSaveInstanceState(outState);
    }

    public void previousStep(final View v) {
        if (currentStep > 0){
            currentStep -= 1;
        } else {
            currentStep = -1;
        }
        addStepDetailsFragment();
    }

    public void nextStep(final View v) {
        if (currentStep < recipe.getSteps().size() - 1){
            currentStep += 1;
            addStepDetailsFragment();
        }
    }

    private void addIngredientsFragment(){
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.recipe = recipe;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, ingredientsFragment)
                .commit();

    }
}