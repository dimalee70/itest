package itest.kz.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.Iterator;

public class CustomViewPager extends ViewPager {

    private float initialXValue;
    private SwipeDirection direction;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection.all;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    //down one is added for smooth scrolling

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (this.IsSwipeAllowed(event)) {
//            return super.onTouchEvent(event);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        if (this.IsSwipeAllowed(event)) {
//            return super.onInterceptTouchEvent(event);
//        }
//
//        return false;
//    }
//
//    private boolean IsSwipeAllowed(MotionEvent event) {
//        if (this.direction == SwipeDirection.all) return true;
//
//        if (direction == SwipeDirection.none)//disable any swipe
//            return false;
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            initialXValue = event.getX();
//            return true;
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            try {
//                float diffX = event.getX() - initialXValue;
//                if (diffX > 0 && direction == SwipeDirection.right) {
//                    // swipe from left to right detected
//                    return true;
//                }
//                if (diffX < 0 && direction == SwipeDirection.left) {
//                    // swipe from right to left detected
//                    return false;
//                }
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }
//
//        return true;
//    }
//
//
//    public void setAllowedSwipeDirection(SwipeDirection direction) {
//        this.direction = direction;
//    }
}
