package com.mahmoud.android.bakingtime.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.data.AndroidImageAssets;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.squareup.picasso.Picasso;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private Recipe[] mRecipes;
    private int selectedPosition = -1;
    public Boolean selectable = false;

    final private RecipesAdapterOnClickHandler mClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipesAdapter(RecipesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mRecipeNameTextView;
        public final ImageView mBackgroundImageView;

        public RecipesAdapterViewHolder(View view) {
            super(view);
            mRecipeNameTextView = (TextView) view.findViewById(R.id.tv_recipe_name);
            mBackgroundImageView = (ImageView) view.findViewById(R.id.iv_background);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (selectable) notifyItemChanged(selectedPosition);
            selectedPosition = getAdapterPosition();
            Recipe recipe = mRecipes[selectedPosition];
            mClickHandler.onClick(recipe);
            if (selectable) notifyItemChanged(selectedPosition);
        }
    }

    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        int layoutIdForListItem = R.layout.recipe_list_item;
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        int minHeight = parent.getHeight() / mRecipes.length;
        if (view.getLayoutParams().height < minHeight){
            view.getLayoutParams().height = minHeight;
        }

        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipes[position];

        holder.mRecipeNameTextView.setText(recipe.getName());

        Context context = holder.mBackgroundImageView.getContext();
        Uri uri = Uri.parse(recipe.getImage());
        int backgroundPlaceholder = (AndroidImageAssets.backgrounds.get((position + 1) % AndroidImageAssets.backgrounds.size()))-1;
        Picasso.with(context).load(uri).placeholder(backgroundPlaceholder).into(holder.mBackgroundImageView);

//        if (selectable && position == selectedPosition){
//            holder.mBorderLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.mBorderLayout.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.length;
    }

    public void setRecipes(Recipe[] recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

}
