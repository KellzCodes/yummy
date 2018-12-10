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

package com.kelldavis.yummy.utilities;

import com.kelldavis.yummy.fragment.StepFragment;

public class Constants {
    static final String UDACITY_BASE_MOVIE_URL = "http://go.udacity.com/android-baking-app-json/";
    public static final String RECIPE_WIDGET_ACTION_UPDATE = "com.kelldavis.yummy.update";
    public static final String BUNDLE_RECIPE_WIDGET_DATA = "com.kelldavis.yummy.recipe_widget_data";
    public static final String BUNDLE_RECIPE_DATA = "com.kelldavis.yummy.recipe_data";
    public static final String INSTANCE_KEY_ADAPTER_POSITION = "instance_key_adapter_position";
    public static final String INSTANCE_KEY_RECIPE = "instance_key_recipe";
    public static final String INSTANCE_KEY_INGREDIENTS_ID = "instance_key_ingredients_id";
    public static final String INSTANCE_KEY_INGREDIENTS_COUNT = "instance_key_ingredients_count";
    public static final String INSTANCE_KEY_STEPS = "instance_key_steps";
    public static final String INSTANCE_KEY_RECIPE_LIST = "instance_key_recipe_list";
    public static final String BUNDLE_STEP_DATA = "com.kelldavis.yummy.step_data";
    public static final String BUNDLE_CURRENT_RECIPE = "com.kelldavis.yummy.current_recipe";
    public static final String BUNDLE_CURRENT_STEP = "com.kelldavis.yummy.current_step";
    public static final String TAG = StepFragment.class.getSimpleName();
}
