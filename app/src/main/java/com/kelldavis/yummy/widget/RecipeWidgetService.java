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

package com.kelldavis.yummy.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.utilities.Constants;

public class RecipeWidgetService extends IntentService {
    public static final String WIDGET_ACTION_UPDATE = Constants.RECIPE_WIDGET_ACTION_UPDATE;
    private static final String RECIPE_WIDGET_DATA = Constants.BUNDLE_RECIPE_WIDGET_DATA;

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(WIDGET_ACTION_UPDATE);
        intent.putExtra(RECIPE_WIDGET_DATA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(RECIPE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Recipe)intent.getParcelableExtra(RECIPE_WIDGET_DATA));
            }
        }
    }

    private void handleActionUpdateWidgets(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
