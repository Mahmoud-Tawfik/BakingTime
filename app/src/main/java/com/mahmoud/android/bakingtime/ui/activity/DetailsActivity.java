package com.mahmoud.android.bakingtime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.fragment.DetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public Recipe recipe;
    private final static String CURRENT_RECIPE = "current_recipe";

    public @BindView(R.id.tv_recipe_name) TextView mRecipeNameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                recipe = intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
                mRecipeNameTextView.setText(recipe.getName());
            }
        }

        if (savedInstanceState == null){
            addDetailsFragmentForRecipe(recipe);
        } else {
            recipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
            addDetailsFragmentForRecipe(recipe);
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
        outState.putParcelable(CURRENT_RECIPE, recipe);
        super.onSaveInstanceState(outState);
    }
}
