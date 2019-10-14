package itest.kz.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import itest.kz.R;

public class CustomTextViewBold extends AppCompatTextView
{
    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    public CustomTextViewBold(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public CustomTextViewBold(Context context)
    {
        super(context);
    }

    public void applyCustomFont (Context context, String fontPath)
    {
        Typeface customFont = FontCache.getTypeface(fontPath, context);
        setTypeface(customFont);
    }

    public void applyCustomFont (Context context, AttributeSet attrs)
    {
        String     fontPathBold = "font/opensans_bold.ttf";
        TypedArray temp     = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextViewBold, 0, 0);
        try
        {
            fontPathBold = temp.getString(R.styleable.CustomTextViewBold_fontPathBold);
        }
        finally
        {
            applyCustomFont(context, fontPathBold);
            temp.recycle();
        }
    }
}
