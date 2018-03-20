package com.cloud.lashou.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hantao on 2017/6/14.
 */

public class SwipeLinearLayout extends LinearLayout implements View.OnTouchListener
        ,GestureDetector.OnGestureListener {
    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    GestureDetector mGestureDetector;
    private SlideListener slideListener;
    public SwipeLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(this);
        setOnTouchListener(this);
        setLongClickable(true);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (slideListener!=null) {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {
//            && Math.abs(velocityX) > FLING_MIN_VELOCITY
                // Fling left
                slideListener.listener(LEFT);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE) {
//             && Math.abs(velocityX) > FLING_MIN_VELOCITY
                // Fling right
                slideListener.listener(RIGHT);
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void setSlideListener(SlideListener slideListener){
        this.slideListener = slideListener;
    }

    public interface SlideListener{
        void listener(int direction);
    }
}
