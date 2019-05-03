package itest.kz.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class AnswerWebView extends WebView
{
    private Context context;
    private double time = 0;
    private double curTime = 0;

    public AnswerWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AnswerWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public AnswerWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public AnswerWebView(Context context)
    {
        super(context);
        this.context = context;
//        init(context);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY)
    {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }


    ////
//
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                downX = event.getX();
//                return true;
//            }
//            case MotionEvent.ACTION_UP: {
//                upX = event.getX();
//                float deltaX = downX - upX;
//
//                if (Math.abs(deltaX) > MIN_DISTANCE) {
//                    if (deltaX < 0) {
//                        this.onLeftToRightSwipe();
//                        return true;
//                    }
//                    if (deltaX > 0) {
//                        this.onRightToLeftSwipe();
//                        return true;
//                    }
//                    return true;
//                }
//
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {


//        if (ev.getAction() == MotionEvent.ACTION_DOWN)
//        {
////            System.out.println("Press");
//            time = System.currentTimeMillis();
////            System.out.println(time);
//
//            return true;
//        }
//        if (ev.getAction() == MotionEvent.ACTION_UP)
//        {
////            System.out.println("UP");
////            System.out.println(System.currentTimeMillis());
//            curTime = System.currentTimeMillis() - time;
////            System.out.println(curTime);
//            time = 0;
//            if (time > 1)
//            {
//                return  false;
//            }
//            else
//                return true;

//            return false;
//        }
//        if (ev.getAction() == MotionEvent.ACTION_MOVE)
//        {
//            System.out.println("Move");
//            return true;
//        }
//        switch (ev.getAction())
//        {
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("Move");
//                return false;
//            case MotionEvent.ACTION_DOWN:
//                System.out.println("Down");
//                return false;
//            case MotionEvent.ACTION_UP:
//                System.out.println("UP");
//                return true;
//            }
        return false;
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent ev)
//    {
//        return true;
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN)
//        {
//            System.out.println("Press");
//            time = System.currentTimeMillis();
//            System.out.println(time);
//
//            return true;
//        }
//        if (ev.getAction() == MotionEvent.ACTION_UP)
//        {
//            System.out.println("UP");
////            System.out.println(System.currentTimeMillis());
//            curTime = System.currentTimeMillis() - time;
//            System.out.println(curTime);
//            time = 0;
//            if (time > 1)
//            {
//                return  true;
//            }
//            else
//                return false;
//        return super.onInterceptTouchEvent(ev);
//    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev)
//    {
////            switch (ev.getAction()) {
////                case MotionEvent.ACTION_DOWN:
////                    return false;
////                case MotionEvent.ACTION_UP:
////                    return true;
////            }
//        return false;
        return true;
    }
}
