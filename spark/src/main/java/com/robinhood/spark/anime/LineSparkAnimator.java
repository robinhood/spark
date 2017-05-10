package com.robinhood.spark.anime;

import android.graphics.Path;
import android.graphics.PathMeasure;

import com.robinhood.spark.SparkView;

/**
 * Line Spark Animator animates spark following the graph line
 */
public class LineSparkAnimator implements SparkAnimator {

    @Override
    public void animation(SparkView view, float animatedValue) {

        Path renderPath = view.getSparkLinePath();

        if(renderPath == null) {
            return;
        }

        // get path length
        PathMeasure pathMeasure = new PathMeasure(renderPath, false);
        float endLength = pathMeasure.getLength();

        if(endLength > 0) {

            float animatedPathLength = animatedValue * endLength;

            renderPath.reset();
            pathMeasure.getSegment(0, animatedPathLength, renderPath, true);

            // must do it to animation happens
            view.setRenderPath(renderPath);
        }

    }

    @Override
    public long getAnimationDuration() {
        return -1;
    }

}
