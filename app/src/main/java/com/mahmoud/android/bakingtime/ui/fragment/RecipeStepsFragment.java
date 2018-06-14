package com.mahmoud.android.bakingtime.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.ui.adapter.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 1/1/18.
 */

public class RecipeStepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{

    public Recipe recipe;

    public Boolean selectable = false;

    private StepsAdapter mStepsAdapter;
    final static String CURRENT_RECIPE = "current_recipe";

    public @BindView(R.id.rv_steps) RecyclerView mStepsRecyclerView;
    public @BindView(R.id.ingredients_button) Button mIngredientsButton;

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onIngredientsSelected();
        void onStepSelected(int step);
    }

    public RecipeStepsFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onIngredientsSelected & onStepSelected");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, rootView);

        mIngredientsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mIngredientsButton.setSelected(true);
                mCallback.onIngredientsSelected();
            }
        });

        selectable = getActivity().findViewById(R.id.step_details_container) != null;

        mStepsAdapter = new StepsAdapter(this);
        mStepsAdapter.selectable = selectable;
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        mStepsAdapter.setSteps(recipe.getSteps());

        return rootView;
    }

    @Override
    public void onClick(int step) {
        mIngredientsButton.setSelected(false);
        mCallback.onStepSelected(step);
        Toast.makeText(this.getContext(), recipe.getSteps().get(step).getShortDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_RECIPE, recipe);
        super.onSaveInstanceState(outState);
    }


}
