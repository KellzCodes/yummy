package com.kelldavis.yummy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.fragment.RecipeDetailFragment;
import com.kelldavis.yummy.fragment.RecipeStepFragment;
import com.kelldavis.yummy.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener {
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private Recipe mRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent callingIntent = getIntent();
        mRecipe = callingIntent.getParcelableExtra(getString(R.string.recipe_key));

        if (findViewById(R.id.recipe_detail_fragment_for_tablet) != null) {
            mTwoPane = true;
            TextView recipeDetailName = findViewById(R.id.tv_recipe_detail_name);
            recipeDetailName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        } else {
            mTwoPane = false;
        }
    }

    public void onStepSelected(int whichStep) {
        if (mTwoPane) {
            RecipeStepFragment recipeStepFragment = (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_fragment_for_tablet);
            recipeStepFragment.updateStepNumberDescriptionAndVideo(whichStep);
        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(getString(R.string.recipe_key), mRecipe);
            intent.putExtra(getString(R.string.which_step_key), whichStep);
            startActivity(intent);
        }
    }
}
