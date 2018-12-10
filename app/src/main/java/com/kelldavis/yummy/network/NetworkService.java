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

package com.kelldavis.yummy.network;

import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.utilities.ApiUtils;

import java.util.ArrayList;

import io.reactivex.Observable;

public class NetworkService {

    private ApiObservable mApiObservable;

    public NetworkService(){
        mApiObservable = ApiUtils.getApiObservable();
    }

    public Observable<ArrayList<Recipe>> networkApiRequestRecipes() {
        return mApiObservable.getRecipeResult();
    }
}
