package com.robinhood.spark.anime;

import java.util.List;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;

import com.robinhood.spark.SparkView;

/**
 * Morph Spark Animation animates each point of graphic from initial position until end position in vertical way
 */
public class MorphSparkAnimator implements SparkAnimator {

    private Path animationPath;
    private List<Float> oldYPoints;

    public MorphSparkAnimator() {
        this.animationPath = new Path();
    }

    public void setOldPoints(final List<Float> oldYPoints) {
        this.oldYPoints = oldYPoints;
    }

    @Override
    public Animator getAnimation(final SparkView sparkView) {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedValue = (float) animation.getAnimatedValue();

                animationPath.reset();
                List<Float> xPoints = sparkView.getXPoints();
                List<Float> yPoints = sparkView.getYPoints();

                if (xPoints != null && yPoints != null) {
                    float step;
                    float y, oldY;
                    int size = xPoints.size();
                    for (int count = 0; count < size; count++) {

                        // get oldY, can be 0 (zero) if current points are larger
                        oldY = oldYPoints != null && oldYPoints.size() > count ? oldYPoints.get(count) : 0f;

                        step = yPoints.get(count) - oldY;
                        y = (step * animatedValue) + oldY;

                        if (count == 0) {
                            animationPath.moveTo(xPoints.get(count), y);
                        } else {
                            animationPath.lineTo(xPoints.get(count), y);
                        }

                    }

                    // must do it to animation happens
                    sparkView.setAnimationPath(animationPath);

                }
            }
        });

        // set animation duration
        animator.setDuration(3000L);

        return animator;
    }

}
