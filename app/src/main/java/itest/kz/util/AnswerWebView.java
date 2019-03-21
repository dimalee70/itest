package itest.kz.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class AnswerWebView extends WebView
{
    private Context context;

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
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return true;
    }
}
