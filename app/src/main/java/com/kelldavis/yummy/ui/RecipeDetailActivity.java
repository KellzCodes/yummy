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

package com.kelldavis.yummy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.fragment.RecipeDetailFragment;
import com.kelldavis.yummy.fragment.StepFragment;
import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.model.Step;
import com.kelldavis.yummy.presenter.RecipeDetailPresenter;
import com.kelldavis.yummy.utilities.Constants;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailPresenter.Callbacks {
    private static final String RECIPE_DATA = Constants.BUNDLE_RECIPE_DATA;

    public static Intent newIntent(Context packageContext, Recipe recipe) {
        Intent intent = new Intent(packageContext, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.fragment_container);
        Recipe recipe = getIntent().getExtras().getParcelable(RECIPE_DATA);

        if (fragment == null) {
            fragment = RecipeDetailFragment.newInstance(recipe);
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();

            if (getResources().getBoolean(R.bool.isTablet)) {
                Fragment newDetail = StepFragment.newInstance(recipe.getSteps().get(0));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetail)
                        .commit();
            }
        }
    }

    @Override
    public void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = StepsActivity.newIntent(this, stepList, currentStep, recipeName);
            startActivity(intent);
        } else {
            Fragment stepFragment = StepFragment.newInstance(stepList.get(currentStep));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, stepFragment)
                    .commit();
        }
    }
}

