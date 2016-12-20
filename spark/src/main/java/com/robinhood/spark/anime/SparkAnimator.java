package com.robinhood.spark.anime;

import android.graphics.Path;

/**
 *  This interface is for animate SparkView when it changes
 */
public interface SparkAnimator {

    /**
     * This is executed before the animation starts, and after the new graph path is set
     * @param newPath The new path, this will be the graph end
     */
    void preAnimation(Path newPath);

    /**
     * This is executed before the animation starts, and after the new graph path is set.
     * It pass the old points and new points values, with no need of get it from path.
     * @param oldXPoints The old X coordinates array
     * @param oldYPoints The old Y coordinates array
     * @param newXPoints The new X coordinates array
     * @param newYPoints The new Y coordinates array
     */
    void preAnimation(Float[] oldXPoints, Float[] oldYPoints, Float[] newXPoints, Float[] newYPoints);

    /**
     * This will do the animation itself, must execute the animation step here
     * @param animatedValue Value represent the animation stage, this go from 0 to 1
     * @return Return the path to show in this part of the animation
     */
    Path getNextPath(float animatedValue);

    /**
     * This will execute after the animation ends
     */
    void endAnimation();

}
