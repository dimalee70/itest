package itest.kz.util;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.reactivex.functions.Action;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class BindingUtils {

    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    @BindingAdapter("android:specText")
    public static void setSpecText(TextView text, Spanned spanned)
    {
//        String html = "<a href=\"http://www.google.com\">Google</a>";
//        Spanned result;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            result = Html.fromHtml(html);
//        }
//        fragmentLoginBinding.firstPartText.setText(result);
//        fragmentLoginBinding.firstPartText.setMovementMethod(LinkMovementMethod.getInstance());
//        Spannable s = new SpannableString(textView.getText());
        Spannable buffer=new SpannableString(spanned);
        URLSpan[] spans = buffer.getSpans(0, buffer.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = buffer.getSpanStart(span);
            int end = buffer.getSpanEnd(span);
            buffer.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            buffer.setSpan(span, start, end, 0);
        }
        text.setText(buffer);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @BindingConversion
    public static View.OnClickListener toOnClickListener(final Action listener) {
        if (listener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        listener.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        } else {
            return null;
        }
    }

    @BindingAdapter("android:src")
    public static void setImageResource(Button button, Drawable resource){
        button.setBackground(resource);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(TextView textView, Drawable resource){
        textView.setBackground(resource);
    }


//    @BindingAdapter({"bind:imageUrl"})
//    public static void loadImage(ImageView view, String imageUrl)
//    {
////        String photo1 = "http://i.imgur.com/DvpvklR.png";
//        Picasso
//                .get()
//                .load((imageUrl == null ||
//                        imageUrl.equals("")?
//                        Constant.EMPTY_PHOTO :
//                        imageUrl))
//                .transform(new CropCircleTransformation())
////                .centerInside()
//                .fit()
//                .into(view);
//    }

    @BindingAdapter("app:backgroundColor")
    public static void setColor(CardView cardView, int color)
    {
        cardView.setCardBackgroundColor(color);
    }

    @BindingAdapter({"app:loadData"})
    public static void loadData( AnswerWebView view, String url)
    {
//        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.setVerticalScrollBarEnabled(true);
//        view.setBackgroundColor(Color.TRANSPARENT);
        view.setHorizontalScrollBarEnabled(true);

//        view.setInitialScale(1);
//        view.getSettings().setLoadWithOverviewMode(true);
//        view.getSettings().setUseWideViewPort(true);
////        .getSettings().setUseWideViewPort(true);
//        view.getSettings().setAppCacheEnabled(true);
//        view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

//        browser.loadData(Constant.MATHJAX + test.getQuestion(), , "UTF-8");
//        webView.loadData(urlString, "text/html; charset=utf-8", "base64");
        view.loadData(Constant.MATHJAX + url, Constant.HTML, Constant.UTF_8 );
//        view.loadDataWithBaseURL("", Constant.MATHJAX + url, "text/html", "utf-8", "");
    }

    @BindingAdapter({"app:loadDataCheck"})
    public static void loadDataCheck( AnswerWebView view, String url)
    {
//        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.setVerticalScrollBarEnabled(true);
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setHorizontalScrollBarEnabled(true);

////        .getSettings().setUseWideViewPort(true);
//        view.getSettings().setAppCacheEnabled(true);
//        view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

//        browser.loadData(Constant.MATHJAX + test.getQuestion(), , "UTF-8");
        view.loadData(Constant.MATHJAX + url, Constant.HTML, Constant.UTF_8 );
//        view.loadDataWithBaseURL("", Constant.MATHJAX + url, "text/html", "utf-8", "");
    }
    @BindingAdapter({"app:loadData"})
    public static void loadData( WebView view, String url)
    {
//        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.setVerticalScrollBarEnabled(true);
        view.setHorizontalScrollBarEnabled(true);
////        .getSettings().setUseWideViewPort(true);
//        view.getSettings().setAppCacheEnabled(true);
//        view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

//        browser.loadData(Constant.MATHJAX + test.getQuestion(), , "UTF-8");
        view.loadData(Constant.MATHJAX + url, Constant.HTML, Constant.UTF_8 );
    }

}
