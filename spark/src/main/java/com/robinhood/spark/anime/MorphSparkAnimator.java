package com.robinhood.spark.anime;

import android.graphics.Path;

/**
 * Point Spark Animation animates each point of graphic from initial position until end position in vertical way
 */
public class MorphSparkAnimator implements SparkAnimator {

    private Path renderPath;
    private Float[] oldYPoints;
    private Float[] newXPoints;
    private Float[] newYPoints;

    @Override
    public void preAnimation(Path newPath) {
        this.renderPath = new Path();
    }

    @Override
    public void preAnimation(Float[] oldXPoints, Float[] oldYPoints, Float[] newXPoints, Float[] newYPoints) {
        this.oldYPoints = oldYPoints;
        this.newXPoints = newXPoints;
        this.newYPoints = newYPoints;
    }

    @Override
    public Path getNextPath(float animatedValue) {

        renderPath.reset();

        float step;
        float y, oldY;
        int size = newYPoints.length;
        for(int count = 0; count < size; count++) {

            // get oldY, can be 0 (zero) if current points are larger
            oldY = oldYPoints != null && oldYPoints.length > count ? oldYPoints[count]: 0f;

            step = newYPoints[count] - oldY;
            y = (step * animatedValue) + oldY;

            if (count == 0) {
                renderPath.moveTo(newXPoints[count], y);
            }
            else {
                renderPath.lineTo(newXPoints[count], y);
            }

        }

        return renderPath;
    }

    @Override
    public void endAnimation() {
        // do nothing
    }

}
