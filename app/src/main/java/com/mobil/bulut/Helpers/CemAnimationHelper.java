package com.mobil.bulut.Helpers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

public class CemAnimationHelper {


    public static void translateYAnimation(final View view, Integer duration, Integer startDelay, Integer fromY, Integer toY) {
        if (view == null) { return; }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.animate()
                    .translationYBy(fromY)
                    .translationY(toY)
                    .setDuration(duration)
                    .setStartDelay(startDelay)
                    .withLayer().setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

                    params.topMargin = 0;
                    params.leftMargin = 0;
                    params.rightMargin = 0;
                    params.bottomMargin = 0;
                    view.setLayoutParams(params);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();

        }
        else {

            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            Animator animator2 = ObjectAnimator.ofFloat(view,View.TRANSLATION_Y,fromY,toY);
            animator2.setDuration(duration);
            animator2.setStartDelay(startDelay);
            animator2.setInterpolator(new LinearInterpolator());
            animator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setLayerType(View.LAYER_TYPE_NONE, null);
                        }
                    });

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator2.start();
        }
    }


    public static void translateXAnimation(final View view, Integer duration, Integer startDelay, Integer fromX, Integer toX) {
        if (view == null) { return; }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.animate()
                    .translationXBy(fromX)
                    .translationX(toX)
                    .setDuration(duration)
                    .setStartDelay(startDelay)
                    .withLayer()
                    .setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

                    params.topMargin = 0;
                    params.leftMargin = 0;
                    params.rightMargin = 0;
                    params.bottomMargin = 0;
                    view.setLayoutParams(params);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

                    params.topMargin = 0;
                    params.leftMargin = 0;
                    params.rightMargin = 0;
                    params.bottomMargin = 0;
                    view.setLayoutParams(params);

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();

        }
        else {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            Animator animator2 = ObjectAnimator.ofFloat(view,View.TRANSLATION_X,fromX,toX);
            animator2.setDuration(duration);
            animator2.setStartDelay(startDelay);
            animator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setLayerType(View.LAYER_TYPE_NONE, null);
                        }
                    });

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator2.start();
        }
    }

    public static void alphaAnimation(final View view, Integer duration, Integer startDelay, Float startValue, Float endValue) {

        if (view == null) { return; }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.animate()
                    .alphaBy(startValue)
                    .alpha(endValue)
                    .setDuration(duration)
                    .setStartDelay(startDelay)
                    .withLayer()
                    .setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
        else {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            Animator animator = ObjectAnimator.ofFloat(view, View.ALPHA, startValue, endValue);
            animator.setDuration(duration);
            animator.setStartDelay(startDelay);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setLayerType(View.LAYER_TYPE_NONE, null);
                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    view.setLayerType(View.LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator.start();
        }
    }
}
