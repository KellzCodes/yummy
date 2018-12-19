package com.kelldavis.yummy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.fragment.RecipeListFragment;
import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.ui.RecipeDetailActivity;

public class RecipeListActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener {

    private final static String TAG = RecipeListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
    }

    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(getString(R.string.recipe_key), recipe);
        startActivity(intent);
    }

}
