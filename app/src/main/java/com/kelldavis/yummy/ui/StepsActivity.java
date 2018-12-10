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

package com.kelldavis.yummy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.databinding.ActivityStepViewpagerBinding;
import com.kelldavis.yummy.fragment.StepFragment;
import com.kelldavis.yummy.model.Step;
import com.kelldavis.yummy.utilities.Constants;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {
    private static final String STEP_DATA = Constants.BUNDLE_STEP_DATA;
    private static final String CURRENT_RECIPE = Constants.BUNDLE_CURRENT_RECIPE;
    private static final String CURRENT_STEP = Constants.BUNDLE_CURRENT_STEP;
    private ViewPager mViewPager;

    private ActivityStepViewpagerBinding binding;

    public static Intent newIntent(Context packageContext, ArrayList<Step> stepList,
                                   int currentStep, String recipeName) {
        Intent intent = new Intent(packageContext, StepsActivity.class);
        intent.putExtra(STEP_DATA, stepList);
        intent.putExtra(CURRENT_STEP, currentStep);
        intent.putExtra(CURRENT_RECIPE, recipeName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_viewpager);

        final ArrayList<Step> stepList = getIntent().getExtras().getParcelableArrayList(STEP_DATA);
        final int currentStep = getIntent().getExtras().getInt(CURRENT_STEP);
        String currentRecipeName = getIntent().getExtras().getString(CURRENT_RECIPE);

        setSupportActionBar(binding.tbToolbar.toolbar);
        binding.tbToolbar.toolbar.setTitle(currentRecipeName);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_activity_step_viewpager);
        for(Step step : stepList) {
            if(step.getId() == 0)
            {
                tabLayout.addTab(tabLayout.newTab().setText("Intro"));
            }
            else {
                tabLayout.addTab(tabLayout.newTab().setText(
                        String.format(getString(R.string.step_number_format), (step.getId()))));
            }
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // Fullscreen mode for non-tablet landscape orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.tbToolbar.toolbar.setVisibility(View.GONE);
            binding.tlActivityStepViewpager.setVisibility(View.GONE);
        }

        mViewPager = (ViewPager) findViewById(R.id.vp_activity_step_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return StepFragment.newInstance(stepList.get(position));
            }
            @Override
            public int getCount() {
                return stepList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mViewPager.setCurrentItem(currentStep);
    }
}

