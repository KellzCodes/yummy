package com.kelldavis.yummy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.model.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.RecipeStepAdapterViewHolder> {
    private static final String TAG = StepAdapter.class.getSimpleName();
    final private StepAdapter.RecipeStepAdapterOnClickHandler mClickHandler;
    private Step[] mSteps;

    public StepAdapter(StepAdapter.RecipeStepAdapterOnClickHandler recipeStepAdapterOnClickHandler) {
        mClickHandler = recipeStepAdapterOnClickHandler;
    }

    @Override
    public StepAdapter.RecipeStepAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_step_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepAdapter.RecipeStepAdapterViewHolder viewHolder = new StepAdapter.RecipeStepAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.RecipeStepAdapterViewHolder holder, int position) {
        int recipeStepNumber = mSteps[position].getId();
        // the recipe introduction gets grouped with the steps but doesn't have a step number in its description
        String stepNumberString = recipeStepNumber > 0 ? String.format("Step %d: ", recipeStepNumber) : "";
        String recipeShortDescription = mSteps[position].getShortDescription();

        holder.tvStepNumberAndShortDescription.setText(String.format(stepNumberString + recipeShortDescription));
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        } else {
            return mSteps.length;
        }
    }

    public void setRecipeStepData(Step[] steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public interface RecipeStepAdapterOnClickHandler {
        void onClick(int whichStep);
    }

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvStepNumberAndShortDescription;

        public RecipeStepAdapterViewHolder(View itemView) {
            super(itemView);
            tvStepNumberAndShortDescription = itemView.findViewById(R.id.tv_step_number_and_short_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int whichStep = getAdapterPosition();
            mClickHandler.onClick(whichStep);
        }
    }
}
