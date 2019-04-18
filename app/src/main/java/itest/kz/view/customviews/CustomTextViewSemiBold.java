package itest.kz.view.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import itest.kz.R;

public class CustomTextViewSemiBold extends AppCompatTextView
{
    public CustomTextViewSemiBold(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    public CustomTextViewSemiBold(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public CustomTextViewSemiBold(Context context)
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
        String     fontPathBold = "opensans_semibold.ttf";
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
//public class HeliVnTextView extends TextView {
//
//    /*
//     * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
//     */
//    private static Typeface mTypeface;
//
//    public HeliVnTextView(final Context context) {
//        this(context, null);
//    }
//
//    public HeliVnTextView(final Context context, final AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public HeliVnTextView(final Context context, final AttributeSet attrs, final int defStyle) {
//        super(context, attrs, defStyle);
//
//        if (mTypeface == null) {
//            mTypeface = Typeface.createFromAsset(context.getAssets(), "HelveticaiDesignVnLt.ttf");
//        }
//        setTypeface(mTypeface);
//    }
//
//}