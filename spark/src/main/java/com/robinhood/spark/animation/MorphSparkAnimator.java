package com.robinhood.spark.animation;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Path;

import com.robinhood.spark.SparkView;

/**
 * Animates each point vertically from the previous position to the current position.
 */
public class MorphSparkAnimator implements SparkAnimator {

    private ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    private Path animationPath = new Path();
    private Path animationFillPath = new Path();
    private List<Float> oldYPoints;

    @Override
    public Animator getAnimation(final SparkView sparkView) {

        final List<Float> xPoints = sparkView.getXPoints();
        final List<Float> yPoints = sparkView.getYPoints();

        if (xPoints.isEmpty() || yPoints.isEmpty()) {
            return null;
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedValue = (float) animation.getAnimatedValue();

                animationPath.reset();
                animationFillPath.reset();

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
                        animationFillPath.moveTo(xPoints.get(count), y);
                    } else {
                        animationPath.lineTo(xPoints.get(count), y);
                        animationFillPath.lineTo(xPoints.get(count), y);
                    }

                }

                // if we're filling the graph in, close the path's circuit
                final Float fillEdge = sparkView.getFillEdge();
                if (fillEdge != null) {
                    final float lastX = sparkView.getScaleHelper().getX(sparkView.getAdapter().getCount() - 1);
                    // line up or down to the fill edge
                    animationFillPath.lineTo(lastX, fillEdge);
                    // line straight left to far edge of the view
                    animationFillPath.lineTo(sparkView.getPaddingStart(), fillEdge);
                    // closes line back on the first point
                    animationFillPath.close();
                }

                // set the updated path for the animation
                sparkView.setAnimationPath(animationPath);
                sparkView.setFillAnimationPath(animationFillPath);

                sparkView.invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                oldYPoints = yPoints;
            }
        });

        return animator;
    }

    public void setDuration(long duration) {
        animator.setDuration(duration);
    }

}
