package com.kelldavis.yummy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.model.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
    private static final String TAG = RecipeAdapter.class.getSimpleName();
    final private RecipeCardAdapterOnClickHandler mClickHandler;
    private Recipe[] mRecipes;

    public RecipeAdapter(RecipeCardAdapterOnClickHandler recipeCardAdapterOnClickHandler) {
        mClickHandler = recipeCardAdapterOnClickHandler;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_recipe_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeAdapterViewHolder viewHolder = new RecipeAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        String recipeTitle = mRecipes[position].getName();
        String recipeImageUrl = mRecipes[position].getImageUrl();
        String recipeBlurb = mRecipes[position].getBlurb();

        Picasso.with(holder.ivRecipePhoto.getContext())
                .load(recipeImageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivRecipePhoto);
        holder.tvRecipeName.setText(recipeTitle);
        holder.tvRecipeBlurb.setText(recipeBlurb);
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        } else {
            return mRecipes.length;
        }
    }

    public void setRecipeCardData(Recipe[] recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public interface RecipeCardAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView ivRecipePhoto;
        final TextView tvRecipeName;
        final TextView tvRecipeBlurb;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            ivRecipePhoto = itemView.findViewById(R.id.iv_recipe_photo);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvRecipeBlurb = itemView.findViewById(R.id.tv_recipe_blurb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = mRecipes[getAdapterPosition()];
            mClickHandler.onClick(recipe);
        }
    }
}
