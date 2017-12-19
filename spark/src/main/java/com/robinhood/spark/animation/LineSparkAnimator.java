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

                // if we're filling the graph in, close the path's circuit
                final Float fillEdge = sparkView.getFillEdge();
                if (fillEdge != null) {
                    final float lastX = lastPos[0];
                    // line up or down to the fill edge
                    linePath.lineTo(lastX, fillEdge);
                    // line straight left to far edge of the view
                    linePath.lineTo(sparkView.getPaddingStart(), fillEdge);
                    // closes line back on the first point
                    linePath.close();
                }

                // set the updated path for the animation
                sparkView.setAnimationPath(linePath);
                sparkView.invalidate();
            }
        });

        return animator;
    }

}
