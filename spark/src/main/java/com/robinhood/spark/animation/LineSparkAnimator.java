package com.robinhood.spark.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;

import com.robinhood.spark.SparkView;

/**
 * Animates the sparkline by path-tracing from the first point to the last.
 */
public class LineSparkAnimator implements SparkAnimator {
    private boolean ended = false;

    @Override
    public Animator getAnimation(final SparkView sparkView) {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        final Path linePath = sparkView.getSparkLinePath();
        
        if(linePath == null) {
            return null;
        }

        // get path length
        final PathMeasure pathMeasure = new PathMeasure(linePath, false);
        final float endLength = pathMeasure.getLength();

        if(endLength <= 0) {
            return null;
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                float animatedPathLength = animatedValue * endLength;
                linePath.reset();

                pathMeasure.getSegment(0, animatedPathLength, linePath, true);

                float lastPos[] = {0f, 0f};
                //get point at animatedValue
                pathMeasure.getPosTan(pathMeasure.getLength() * animatedValue, lastPos, null);

                // set the updated path for the animation
                sparkView.setAnimationPath(linePath);
                sparkView.invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ended = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ended = true;
                //Once the anim has ended the path needs to be closed properly, so force a rebuild of the path
                sparkView.rebuildPath();
                sparkView.invalidate();
            }
        });

        return animator;
    }

    public Boolean hasFinishedAnimating() { return ended; }
}
