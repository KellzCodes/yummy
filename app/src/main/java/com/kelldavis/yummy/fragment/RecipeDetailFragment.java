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

package com.kelldavis.yummy.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.adapter.StepAdapter;
import com.kelldavis.yummy.databinding.FragmentRecipeDetailBinding;
import com.kelldavis.yummy.model.Ingredient;
import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.model.Step;
import com.kelldavis.yummy.presenter.RecipeDetailPresenter;
import com.kelldavis.yummy.presenter.RecipeDetailPresenterViewContract;
import com.kelldavis.yummy.utilities.Constants;
import com.kelldavis.yummy.utilities.OnItemClickListener;

import java.util.ArrayList;

public class RecipeDetailFragment extends Fragment implements RecipeDetailPresenterViewContract.View {
    private static final String BUNDLE_RECIPE_DATA = Constants.BUNDLE_RECIPE_DATA;
    private static final String INSTANCE_KEY_ADAPTER_POSITION = Constants.INSTANCE_KEY_ADAPTER_POSITION;
    private final String INSTANCE_KEY_RECIPE = Constants.INSTANCE_KEY_RECIPE;
    private final String INSTANCE_KEY_INGREDIENTS_ID = Constants.INSTANCE_KEY_INGREDIENTS_ID;
    private final String INSTANCE_KEY_INGREDIENTS_COUNT = Constants.INSTANCE_KEY_INGREDIENTS_COUNT;
    private final String INSTANCE_KEY_STEPS = Constants.INSTANCE_KEY_STEPS;

    private ShareActionProvider mShareActionProvider;
    private FragmentRecipeDetailBinding binding;
    private Recipe mRecipe;
    private RecipeDetailPresenter mRecipeDetailPresenter;
    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;
    private int mStepAdapterSavedPosition = 0;
    private ArrayList<Step> mStepList = new ArrayList<>();
    private ArrayList<TextView> mIngredientList = new ArrayList<>();

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECIPE_DATA, recipe);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if ((arguments != null) && (arguments.containsKey(BUNDLE_RECIPE_DATA))) {
            mRecipe = arguments.getParcelable(BUNDLE_RECIPE_DATA);
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_KEY_ADAPTER_POSITION)) {
            mStepAdapterSavedPosition = savedInstanceState.getInt(INSTANCE_KEY_ADAPTER_POSITION);
        }
        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(INSTANCE_KEY_RECIPE)) {
                mRecipe = savedInstanceState.getParcelable(INSTANCE_KEY_RECIPE);
            }
            if(savedInstanceState.containsKey(INSTANCE_KEY_STEPS)) {
                mStepList = savedInstanceState.getParcelableArrayList(INSTANCE_KEY_STEPS);
            }
            if(savedInstanceState.containsKey(INSTANCE_KEY_INGREDIENTS_COUNT)) {
                int ingredientCount = savedInstanceState.getInt(INSTANCE_KEY_INGREDIENTS_COUNT);
                for(int i=0; i<ingredientCount; i++) {
                    TextView textView = new TextView(this.getContext());
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setPadding(10,12,10,12);
                    textView.setText("- " + String.valueOf(mRecipe.getIngredients().get(i).getQuantity()) +
                            String.valueOf(mRecipe.getIngredients().get(i).getMeasure()) + " " + mRecipe.getIngredients().get(i).getIngredient());
                    mIngredientList.add(textView);
                }
            }
        }
        mRecipeDetailPresenter = new RecipeDetailPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        final View view = binding.getRoot();

        binding.tbToolbar.toolbar.setTitle(mRecipe.getName());
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbToolbar.toolbar);

        //Save dynamically created textview state on rotation
        if(mIngredientList.size()>0) {
            for(TextView tvIngredientView : mIngredientList) {
                binding.llIngredientChecklist.addView(tvIngredientView);
            }
        } else {
            if (mRecipe.getIngredients() != null && mRecipe.getIngredients().size() > 0) {
                for (Ingredient ingredient : mRecipe.getIngredients()) {
                    TextView textView = new TextView(this.getContext());
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setPadding(10,12,10,12);
                    textView.setText("- "+String.valueOf(ingredient.getQuantity()) +
                            String.valueOf(ingredient.getMeasure()) + " " + ingredient.getIngredient());
                    binding.llIngredientChecklist.addView(textView);
                    mIngredientList.add(textView);
                }
            }
        }
        if (mRecipe.getSteps() != null && mRecipe.getSteps().size() > 0 && mStepList.size()==0) {
            mStepList.addAll(mRecipe.getSteps());
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_detail_steps_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mStepAdapter = new StepAdapter(mStepList, new OnItemClickListener<Step>() {
            @Override
            public void onClick(Step step, View view) {
                mRecipeDetailPresenter.stepClicked(mStepList, step.getId(), mRecipe.getName(), view);
            }
        });
        mStepAdapter.setStepAdapterCurrentPosition(mStepAdapterSavedPosition);
        mRecyclerView.setAdapter(mStepAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.recipe_detail_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_detail_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent(createShareIntent());
    }
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
        String msg = mRecipe.getName() + "\n" + "----\n" +
                getString(R.string.ingredients_title) + ":\n" + "----\n";
        for(TextView ingredient : mIngredientList){
            msg += ingredient.getText() + "\n";
        }
        msg += getString(R.string.steps_title) + ":\n" + "----\n";
        for(Step step : mStepList){
            msg += step.getShortDescription() + "\n";
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        return shareIntent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INSTANCE_KEY_ADAPTER_POSITION, mStepAdapter.getStepAdapterCurrentPosition());
        outState.putParcelable(INSTANCE_KEY_RECIPE, mRecipe);
        outState.putParcelableArrayList(INSTANCE_KEY_STEPS, mStepList);
        outState.putInt(INSTANCE_KEY_INGREDIENTS_COUNT, mIngredientList.size());
    }
}