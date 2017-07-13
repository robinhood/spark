package com.robinhood.spark.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;

import com.robinhood.spark.SparkView;

/**
 * Animates the sparkline by path-tracing from the first point to the last.
 */
public class LineSparkAnimator implements SparkAnimator {

    @Override
    public Animator getAnimation(final SparkView sparkView) {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedValue = (float) animation.getAnimatedValue();

                Path linePath = sparkView.getSparkLinePath();

                if(linePath == null) {
                    return;
                }

                // get path length
                PathMeasure pathMeasure = new PathMeasure(linePath, false);
                float endLength = pathMeasure.getLength();

                if(endLength > 0) {

                    float animatedPathLength = animatedValue * endLength;

                    linePath.reset();
                    pathMeasure.getSegment(0, animatedPathLength, linePath, true);

                    // set the updated path for the animation
                    sparkView.setAnimationPath(linePath);
                }

            }
        });

        return animator;
    }

}
