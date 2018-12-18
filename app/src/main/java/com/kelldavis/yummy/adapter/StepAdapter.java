/*
 * Copyright (C) 2018 Kelli Davis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kelldavis.yummy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.model.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {
    private static final String TAG = StepAdapter.class.getSimpleName();
    final private StepAdapter.StepAdapterOnClickHandler mClickHandler;
    private Step[] mRecipeSteps;

    public StepAdapter(StepAdapter.StepAdapterOnClickHandler stepAdapterOnClickHandler) {
        mClickHandler = stepAdapterOnClickHandler;
    }

    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_step_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepAdapter.StepAdapterViewHolder viewHolder = new StepAdapter.StepAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepAdapterViewHolder holder, int position) {
        int recipeStepNumber = mRecipeSteps[position].getId();
        // the recipe introduction gets grouped with the steps but doesn't have a step number in its description
        String stepNumberString = recipeStepNumber > 0 ? String.format("Step %d: ", recipeStepNumber) : "";
        String recipeShortDescription = mRecipeSteps[position].getShortDescription();

        holder.tvStepNumberAndShortDescription.setText(String.format(stepNumberString + recipeShortDescription));
    }

    @Override
    public int getItemCount() {
        if (mRecipeSteps == null) {
            return 0;
        } else {
            return mRecipeSteps.length;
        }
    }

    public void setRecipeStepData(Step[] recipeSteps) {
        mRecipeSteps = recipeSteps;
        notifyDataSetChanged();
    }

    public interface StepAdapterOnClickHandler {
        void onClick(int whichStep);
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvStepNumberAndShortDescription;

        public StepAdapterViewHolder(View itemView) {
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
