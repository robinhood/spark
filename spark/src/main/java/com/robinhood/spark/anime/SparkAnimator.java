package com.robinhood.spark.anime;

import com.robinhood.spark.SparkView;

/**
 *  This interface is for animate SparkView when it changes
 */
public interface SparkAnimator {

    /**
     * Must do animation in Spark graphic.
     * At the end, must call the spark.setRenderPath(renderPath), to draw the path in this animation step
     * @param view The main SparkView object
     * @param animatedValue Value represents the animation stage, this goes from 0 to 1
     */
    void animation(final SparkView view, final float animatedValue);

    /**
     * Must return duration of animation, in milliseconds.
     * If it return any negative value, Spark will set to default shor animation time
     */
    long getAnimationDuration();

}
