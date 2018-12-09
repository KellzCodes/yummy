package com.kelldavis.yummy.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.utilities.Constants;

public class RecipeWidgetService extends IntentService {
    public static final String RECIPE_WIDGET_ACTION_UPDATE = Constants.RECIPE_WIDGET_ACTION_UPDATE;
    private static final String BUNDLE_RECIPE_WIDGET_DATA = Constants.BUNDLE_RECIPE_WIDGET_DATA;

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(RECIPE_WIDGET_ACTION_UPDATE);
        intent.putExtra(BUNDLE_RECIPE_WIDGET_DATA, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RECIPE_WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Recipe)intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA));
            }
        }
    }

    private void handleActionUpdateWidgets(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
