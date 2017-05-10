package com.robinhood.spark.anime;

import java.util.List;

import android.graphics.Path;
import android.graphics.PointF;

import com.robinhood.spark.SparkView;

/**
 * Morph Spark Animation animates each point of graphic from initial position until end position in vertical way
 */
public class MorphSparkAnimator implements SparkAnimator {

    private Path renderPath;
    private List<PointF> oldPoints;

    private long duration = -1;

    public MorphSparkAnimator() {
        this.renderPath = new Path();
    }

    public void setOldPoints(final List<PointF> oldPoints) {
        this.oldPoints = oldPoints;
    }

    @Override
    public void animation(SparkView view, float animatedValue) {

        renderPath.reset();
        List<PointF> currPoints = view.getPoints();

        if(currPoints != null) {
            float step;
            float y, oldY;
            int size = currPoints.size();
            for (int count = 0; count < size; count++) {

                // get oldY, can be 0 (zero) if current points are larger
                oldY = oldPoints != null && oldPoints.size() > count ? oldPoints.get(count).y : 0f;

                step = currPoints.get(count).y - oldY;
                y = (step * animatedValue) + oldY;

                if (count == 0) {
                    renderPath.moveTo(currPoints.get(count).x, y);
                } else {
                    renderPath.lineTo(currPoints.get(count).x, y);
                }

            }

            // must do it to animation happens
            view.setRenderPath(renderPath);

        }

    }

    /**
     * Example how to set animation duration
     * @param duration Duration of the animation in milliseconds
     */
    public void setAnimationDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public long getAnimationDuration() {
        return duration;
    }

}
