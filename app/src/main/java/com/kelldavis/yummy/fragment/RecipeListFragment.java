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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.adapter.RecipeAdapter;
import com.kelldavis.yummy.databinding.FragmentRecipeListBinding;
import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.network.NetworkService;
import com.kelldavis.yummy.presenter.RecipeListPresenter;
import com.kelldavis.yummy.presenter.RecipeListPresenterViewContract;
import com.kelldavis.yummy.utilities.Constants;
import com.kelldavis.yummy.utilities.OnItemClickListener;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment
        implements RecipeListPresenterViewContract.View {
    private final String INSTANCE_KEY_RECIPE_LIST = Constants.INSTANCE_KEY_RECIPE_LIST;

    private FragmentRecipeListBinding binding;
    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;
    private ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private RecipeListPresenter mRecipeListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(INSTANCE_KEY_RECIPE_LIST)) {
                mRecipeList = savedInstanceState.getParcelableArrayList(INSTANCE_KEY_RECIPE_LIST);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false);
        final View view = binding.getRoot();
        binding.tbToolbar.toolbar.setTitle(getContext().getResources().getString(R.string.app_name));

        mRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.recipe_list_columns)));

        mRecipeListPresenter = new RecipeListPresenter(this, new NetworkService());

        mAdapter = new RecipeAdapter(mRecipeList, new OnItemClickListener<Recipe>() {
            @Override
            public void onClick(Recipe recipe, View view) {
                mRecipeListPresenter.recipeClicked(recipe, view);
            }
        });
        mRecipeRecyclerView.setAdapter(mAdapter);

        if(mRecipeList == null || mRecipeList.size() == 0) {
            mRecipeListPresenter.fetchRecipes();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecipeListPresenter.viewDestroy();
    }

    @Override
    public void updateAdapter(ArrayList<Recipe> recipeList) {
        mRecipeList.clear();
        mRecipeList.addAll(recipeList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displaySnackbarMessage(int stringResId) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.cl_list_container),
                getString(stringResId),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INSTANCE_KEY_RECIPE_LIST, mRecipeList);
        super.onSaveInstanceState(outState);
    }
}

