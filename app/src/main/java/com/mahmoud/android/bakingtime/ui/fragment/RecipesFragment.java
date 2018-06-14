package com.mahmoud.android.bakingtime.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.recipesapi.RecipesApiClient;
import com.mahmoud.android.bakingtime.recipesapi.RecipesApiInterface;
import com.mahmoud.android.bakingtime.ui.adapter.RecipesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragment extends Fragment implements RecipesAdapter.RecipesAdapterOnClickHandler {
    private static final String TAG = RecipesFragment.class.getName();

    RecipesApiInterface apiService = RecipesApiClient.getClient().create(RecipesApiInterface.class);
    Recipe[] mRecipes = new Recipe[]{};

    public Boolean selectable = false;

    private RecipesAdapter mRecipesAdapter;
    final static String RECIPE_LIST = "recipe_list";

    public @BindView(R.id.rv_recipes) RecyclerView mRecipesRecyclerView;

    OnRecipeClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }

    public RecipesFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, rootView);

        selectable = getActivity().findViewById(R.id.details_container) != null;

        mRecipesAdapter = new RecipesAdapter(this);
        mRecipesAdapter.selectable = selectable;
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);
        mRecipesAdapter.setRecipes(mRecipes);

        loadRecipes();

        return rootView;
    }

    void loadRecipes(){
//        Call<Recipe[]> loadRecipeCall = apiService.loadRecipeList();
        apiService.loadRecipeList().enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(Call<Recipe[]> call, Response<Recipe[]> response) {
                if(response.body() !=null) {
                    mRecipes = response.body();
                    mRecipesAdapter.setRecipes(mRecipes);
                } else {
                    Log.d(TAG,"Failed to retrieve recipe list - Null response");
                }
            }

            @Override
            public void onFailure(Call<Recipe[]>call, Throwable t) {
                Log.d(TAG,"Failed to retrieve recipe list - failed" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onClick(Recipe recipe) {
        mCallback.onRecipeSelected(recipe);
        Toast.makeText(this.getContext(), recipe.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(RECIPE_LIST, mRecipes);
        super.onSaveInstanceState(outState);
    }
}
