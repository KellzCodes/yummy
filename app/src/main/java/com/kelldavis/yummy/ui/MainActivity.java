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

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.kelldavis.yummy.fragment.RecipeListFragment;
import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.presenter.RecipeListPresenter;
import com.kelldavis.yummy.widget.RecipeWidgetService;

public class MainActivity extends BaseActivity implements RecipeListPresenter.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }

    @Override
    public void recipeSelected(Recipe recipe) {
        Intent intent = RecipeDetailActivity.newIntent(this, recipe);
        RecipeWidgetService.startActionUpdateRecipeWidgets(this, recipe);
        startActivity(intent);
    }
}

