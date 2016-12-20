package com.robinhood.spark.anime;

import android.graphics.Path;
import android.graphics.PathMeasure;

/**
 * Line Spark Animator animates spark following the graph line
 */
public class LineSparkAnimator implements SparkAnimator {

    private Path renderPath;
    private PathMeasure pathMeasure;
    private float endLength;

    @Override
    public void preAnimation(Path newPath) {

        this.renderPath = new Path();

        // get path length
        pathMeasure = new PathMeasure(newPath, false);

        endLength = pathMeasure.getLength();

    }

    @Override
    public void preAnimation(Float[] oldXPoints, Float[] oldYPoints, Float[] newXPoints, Float[] newYPoints) {
        // do nothing
    }

    @Override
    public Path getNextPath(float animetedValue) {

        if(endLength > 0) {

            float animatedPathLength = animetedValue * endLength;

            renderPath.reset();
            pathMeasure.getSegment(0, animatedPathLength, renderPath, true);

        }

        return renderPath;
    }

    @Override
    public void endAnimation() {

    }

}
