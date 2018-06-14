package com.mahmoud.android.bakingtime.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.RecipesWidget;
import com.mahmoud.android.bakingtime.model.Ingredient;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.fragment.RecipesFragment;
import com.mahmoud.android.bakingtime.ui.fragment.RecipesFragment.OnRecipeClickListener;

public class RecipesActivity extends AppCompatActivity implements OnRecipeClickListener{

    private Recipe selectedRecipe;
    private final static String SELECTED_RECIPE = "selected_recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipesFragment recipesFragment = new RecipesFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.recipes_container, recipesFragment)
                .commit();
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        updateWidget(recipe);

        Intent intentToStartRecipeDetailsActivity = new Intent(this, RecipeDetailsActivity.class);
        intentToStartRecipeDetailsActivity.putExtra(Intent.EXTRA_TEXT, recipe);
        startActivity(intentToStartRecipeDetailsActivity);
    }

    void updateWidget(Recipe recipe){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String ingredients = "";
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            Ingredient ingredient = recipe.getIngredients().get(i);
            ingredients += ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient() + (i == (recipe.getIngredients().size() - 1) ? "" : ";,");
        }
        prefsEditor.putString("desired_recipe_ingredients", ingredients);
        prefsEditor.putString("desired_recipe_name", recipe.getName());
        prefsEditor.commit();

        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] ids = manager.getAppWidgetIds(new ComponentName(this,RecipesWidget.class));
        Intent updateIntent = new Intent();
        updateIntent.putExtra(RecipesWidget.WIDGET_IDS_KEY, ids);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        this.sendBroadcast(updateIntent);
    }

}
