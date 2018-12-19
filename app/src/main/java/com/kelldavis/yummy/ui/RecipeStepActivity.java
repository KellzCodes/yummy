package com.kelldavis.yummy.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kelldavis.yummy.R;

public class RecipeStepActivity extends AppCompatActivity {
    private static final String TAG = RecipeStepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
    }
}
