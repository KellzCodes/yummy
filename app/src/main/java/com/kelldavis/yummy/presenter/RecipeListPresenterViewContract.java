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

package com.kelldavis.yummy.presenter;

import android.support.annotation.StringRes;

import com.kelldavis.yummy.model.Recipe;

import java.util.ArrayList;

public interface RecipeListPresenterViewContract {
    interface View {

        void updateAdapter(ArrayList<Recipe> recipeList);

        void displaySnackbarMessage(@StringRes int stringResId);

        boolean isActive();

    }

    interface Presenter {

        void fetchRecipes();

        void recipeClicked(Recipe recipe, android.view.View view);

        void viewDestroy();

    }
}

