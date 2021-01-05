package org.techtown.mydiarytop.data;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class AnimationLooper{

    public static void start(final View v, final int animationResId){
        v.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Animation a = AnimationUtils.loadAnimation(v.getContext(), animationResId);
                a.setAnimationListener(new AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {
                        animation.reset();
                        animation.startNow();
                    }
                });
                v.startAnimation(a);
            }
        });
    }

    public static void stop(final View v){
        v.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Animation a = v.getAnimation();
                if(a != null){
                    a.setAnimationListener(null);
                    a.cancel();
                }
                v.setAnimation(null);
            }
        });
    }

}