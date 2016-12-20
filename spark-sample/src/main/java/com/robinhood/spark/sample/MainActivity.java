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
import android.widget.TextView;

import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;
import com.robinhood.spark.anime.LineSparkAnimator;
import com.robinhood.spark.anime.MorphSparkAnimator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SparkView sparkView;
    private RandomizedAdapter adapter;
    private TextView scrubInfoTextView;

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
        findViewById(R.id.random_button_line).setOnClickListener(this);
        findViewById(R.id.random_button_point).setOnClickListener(this);

        scrubInfoTextView = (TextView) findViewById(R.id.scrub_info_textview);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.random_button_line:
                sparkView.setSparkAnimator(new LineSparkAnimator());
                adapter.randomize();
                break;

            case R.id.random_button_point:
                sparkView.setSparkAnimator(new MorphSparkAnimator());
                adapter.randomize();
                break;

            case R.id.random_button:
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
}
