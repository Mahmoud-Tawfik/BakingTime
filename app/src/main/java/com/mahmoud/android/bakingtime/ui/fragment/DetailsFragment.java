package com.mahmoud.android.bakingtime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.ui.adapter.IngredientsAdapter;
import com.mahmoud.android.bakingtime.ui.adapter.StepsAdapter;
import com.mahmoud.android.bakingtime.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {
    public Recipe recipe;

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;

    public @BindView(R.id.rv_ingredients) RecyclerView mIngredientsRecyclerView;
    public @BindView(R.id.rv_steps) RecyclerView mStepsRecyclerView;

    public DetailsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);

        if (recipe != null){
            mIngredientsAdapter = new IngredientsAdapter();
            mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
            mIngredientsAdapter.setIngredients(recipe.getIngredients());

            mStepsAdapter = new StepsAdapter();
            mStepsRecyclerView.setAdapter(mStepsAdapter);
            mStepsAdapter.setSteps(recipe.getSteps());
        }

        return rootView;
    }
}
