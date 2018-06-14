package com.mahmoud.android.bakingtime.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.adapter.IngredientsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 1/1/18.
 */

public class IngredientsFragment extends Fragment {
    public Recipe recipe;

    private IngredientsAdapter mIngredientsAdapter;

    public @BindView(R.id.rv_ingredients) RecyclerView mIngredientsRecyclerView;

    public IngredientsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        if (recipe != null){
            mIngredientsAdapter = new IngredientsAdapter();
            mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
            mIngredientsAdapter.setIngredients(recipe.getIngredients());
        }

        return rootView;
    }
}
