package com.robinhood.spark.animation;

import android.animation.Animator;

import com.robinhood.spark.SparkView;

/**
 *  This interface is for animate SparkView when it changes
 */
public interface SparkAnimator {

    /**
     * Returns an Animator that performs the desired animation. Must call {@link SparkView#setAnimationPath} for each animation frame.
     * See {@link LineSparkAnimator} and {@link MorphSparkAnimator} for examples.
     * @param sparkView The SparkView object
     */
    Animator getAnimation(final SparkView sparkView);

}
