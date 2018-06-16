package com.mahmoud.android.bakingtime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.fragment.IngredientsFragment;
import com.mahmoud.android.bakingtime.ui.fragment.RecipeStepsFragment;
import com.mahmoud.android.bakingtime.ui.fragment.RecipeStepsFragment.OnStepClickListener;
import com.mahmoud.android.bakingtime.ui.fragment.StepDetailsFragment;

/**
 * Created by Mahmoud on 1/1/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements OnStepClickListener {
    private boolean mTwoPane;
    private Recipe recipe;
    private int currentStep = -1;
    private final static String CURRENT_RECIPE = "current_recipe";
    private final static String CURRENT_STEP = "current_step";

    StepDetailsFragment stepDetailsFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mTwoPane = findViewById(R.id.step_details_container) != null;

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                recipe = intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
            }
        }

        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.recipe = recipe;
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, recipeStepsFragment)
                    .commit();
        } else {
            recipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
            currentStep = savedInstanceState.getInt(CURRENT_STEP, -1);
            stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "StepDetailsFragment");

            RecipeStepsFragment stepsFragment = (RecipeStepsFragment) getSupportFragmentManager().findFragmentById(R.id.steps_container);
            stepsFragment.recipe = recipe;
            if (mTwoPane){
                if (stepDetailsFragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_details_container, stepDetailsFragment)
                            .commit();
                } else if (currentStep >= 0){
                    addStepDetailsFragmentForStep(currentStep);
                } else {
                    addIngredientsFragment();
                }
            }
        }

        if (recipe != null)
            setTitle(recipe.getName());

    }

    @Override
    public void onIngredientsSelected(){
        if (mTwoPane) {
            addIngredientsFragment();
        } else {
            Intent intentToStartStepDetailsActivity = new Intent(this, StepDetailsActivity.class);
            intentToStartStepDetailsActivity.putExtra(CURRENT_RECIPE, recipe);
            intentToStartStepDetailsActivity.putExtra(CURRENT_STEP, -1);
            startActivity(intentToStartStepDetailsActivity);
        }
    }

    @Override
    public void onStepSelected(int step) {
        if (mTwoPane) {
            addStepDetailsFragmentForStep(step);
        } else {
            Intent intentToStartStepDetailsActivity = new Intent(this, StepDetailsActivity.class);
            intentToStartStepDetailsActivity.putExtra(CURRENT_RECIPE, recipe);
            intentToStartStepDetailsActivity.putExtra(CURRENT_STEP, step);
            startActivity(intentToStartStepDetailsActivity);
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

    private void addStepDetailsFragmentForStep(int step){
        stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.recipe = recipe;
        stepDetailsFragment.currentStep = step;
        currentStep = step;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_details_container, stepDetailsFragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_RECIPE, recipe);
        outState.putInt(CURRENT_STEP, currentStep);
        getSupportFragmentManager().putFragment(outState, "StepDetailsFragment", stepDetailsFragment);
        super.onSaveInstanceState(outState);
    }
}
