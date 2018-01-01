package com.mahmoud.android.bakingtime.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {
    private List<Ingredient> mIngredients;

    public IngredientsAdapter() {
    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        int layoutIdForListItem = R.layout.ingredient_list_item;
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new IngredientsAdapterViewHolder(view);
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
//        public final ImageView mIconImageView;
        public final TextView mQuantityTextView;
        public final TextView mIngredientTextView;

        public IngredientsAdapterViewHolder(View view) {
            super(view);
//            mIconImageView = (ImageView) view.findViewById(R.id.iv_icon);
            mQuantityTextView = (TextView) view.findViewById(R.id.tv_quantity);
            mIngredientTextView = (TextView) view.findViewById(R.id.tv_ingredient);
        }
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.mQuantityTextView.setText(ingredient.getQuantity().toString() + " " + ingredient.getMeasure());
        holder.mIngredientTextView.setText(ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    public void setIngredients(List<Ingredient> ingredients){
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

}
