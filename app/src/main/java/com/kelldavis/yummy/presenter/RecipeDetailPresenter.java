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

import android.view.View;

import com.kelldavis.yummy.model.Step;

import java.util.ArrayList;

public class RecipeDetailPresenter implements RecipeDetailPresenterViewContract.Presenter {

    private final RecipeDetailPresenterViewContract.View mView;

    public interface Callbacks {
        void stepSelected(ArrayList<Step> stepList, int currentStep, String recipeName);
    }

    public RecipeDetailPresenter(RecipeDetailPresenterViewContract.View view) {
        this.mView = view;
    }

    @Override
    public void stepClicked(ArrayList<Step> stepList, int currentStep, String recipeName, View view) {
        ((Callbacks) view.getContext()).stepSelected(stepList, currentStep, recipeName);
    }
}
