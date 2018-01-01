package com.mahmoud.android.bakingtime.recipesapi;

import com.mahmoud.android.bakingtime.model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApiInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Recipe[]> loadRecipeList();
}