/**
 * Copyright (C) 2016 Robinhood Markets, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.robinhood.spark.sample;

import java.util.Random;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;
import com.robinhood.spark.anime.LineSparkAnimator;
import com.robinhood.spark.anime.MorphSparkAnimator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SparkView sparkView;
    private RandomizedAdapter adapter;
    private TextView scrubInfoTextView;

    private AnimationType animationSelected;

    private enum AnimationType {
        NONE,
        LINE,
        MORPH
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sparkView = (SparkView) findViewById(R.id.sparkview);

        adapter = new RandomizedAdapter();
        sparkView.setAdapter(adapter);
        sparkView.setScrubListener(new SparkView.OnScrubListener() {
            @Override
            public void onScrubbed(Object value) {
                if (value == null) {
                    scrubInfoTextView.setText(R.string.scrub_empty);
                } else {
                    scrubInfoTextView.setText(getString(R.string.scrub_format, value));
                }
            }
        });

        findViewById(R.id.random_button).setOnClickListener(this);

        scrubInfoTextView = (TextView) findViewById(R.id.scrub_info_textview);

        // set select
        Spinner animationSelect = (Spinner) findViewById(R.id.animation_select);
        if(animationSelect != null) {
            animationSelect.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.animations)));
            animationSelect.setOnItemSelectedListener(new AnimationItemSelectedListener(this));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.random_button:
            default:
                randomize();
                break;
        }

    }

    public void setAnimationType(AnimationType type) {
        animationSelected = type;
    }

    public void randomize() {

        switch(animationSelected) {
            case LINE:
                sparkView.setSparkAnimator(new LineSparkAnimator());
                adapter.randomize();
                break;

            case MORPH:
                // set animator
                MorphSparkAnimator animator = new MorphSparkAnimator();
                animator.setOldPoints(sparkView.getPoints());
                animator.setAnimationDuration(3000L);

                sparkView.setSparkAnimator(animator);
                adapter.randomize();
                break;

            default:
                sparkView.setSparkAnimator(null);
                adapter.randomize();
                break;
        }


    }

    public static class RandomizedAdapter extends SparkAdapter {
        private final float[] yData;
        private final Random random;

        public RandomizedAdapter() {
            random = new Random();
            yData = new float[50];
            randomize();
        }

        public void randomize() {
            for (int i = 0, count = yData.length; i < count; i++) {
                yData[i] = random.nextFloat();
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return yData.length;
        }

        @Override
        public Object getItem(int index) {
            return yData[index];
        }

        @Override
        public float getY(int index) {
            return yData[index];
        }
    }

    private static class AnimationItemSelectedListener implements AdapterView.OnItemSelectedListener {

        private MainActivity activity;

        public AnimationItemSelectedListener(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch(position) {
                case 2:
                    activity.setAnimationType(AnimationType.LINE);
                    break;

                case 3:
                    activity.setAnimationType(AnimationType.MORPH);
                    break;

                default:
                    activity.setAnimationType(AnimationType.NONE);
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            activity.setAnimationType(AnimationType.NONE);
        }

    }

}
