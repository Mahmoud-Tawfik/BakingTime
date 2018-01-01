package com.mahmoud.android.bakingtime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.fragment.DetailsFragment;
import com.mahmoud.android.bakingtime.ui.fragment.RecipesFragment;
import com.mahmoud.android.bakingtime.ui.fragment.RecipesFragment.OnRecipeClickListener;

public class RecipesActivity extends AppCompatActivity implements OnRecipeClickListener{

    private boolean mTwoPane;
    private Recipe selectedRecipe;
    private final static String SELECTED_RECIPE = "selected_recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mTwoPane = findViewById(R.id.details_container) != null;

        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipesFragment recipesFragment = new RecipesFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.recipes_container, recipesFragment)
                    .commit();
        } else {
            selectedRecipe = savedInstanceState.getParcelable(SELECTED_RECIPE);
            if (selectedRecipe != null && mTwoPane){
                addDetailsFragmentForRecipe(selectedRecipe);
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.details_container));
            }
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        selectedRecipe = recipe;
        if (mTwoPane) {
            addDetailsFragmentForRecipe(recipe);
        } else {
            Intent intentToStartDetailsActivity = new Intent(this, DetailsActivity.class);
            intentToStartDetailsActivity.putExtra(Intent.EXTRA_TEXT, recipe);
            startActivity(intentToStartDetailsActivity);
        }
    }

    private void addDetailsFragmentForRecipe(Recipe recipe){
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.recipe = recipe;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.details_container, detailsFragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_RECIPE, selectedRecipe);
        super.onSaveInstanceState(outState);
    }

}
