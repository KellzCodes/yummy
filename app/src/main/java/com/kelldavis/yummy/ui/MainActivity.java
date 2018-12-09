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

