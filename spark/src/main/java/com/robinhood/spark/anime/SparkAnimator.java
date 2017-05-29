package com.robinhood.spark.anime;

import android.animation.Animator;

import com.robinhood.spark.SparkView;

/**
 *  This interface is for animate SparkView when it changes
 */
public interface SparkAnimator {

    /**
     * Must do animation in Spark graphic.
     * At the end, must call the spark.setAnimationPath(renderPath), to draw the path in this animation step
     * @param sparkView The main SparkView object
     */
    Animator getAnimation(final SparkView sparkView);

}
