package itest.kz.util;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import io.reactivex.functions.Action;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class BindingUtils {

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
        view.setHorizontalScrollBarEnabled(true);
////        .getSettings().setUseWideViewPort(true);
//        view.getSettings().setAppCacheEnabled(true);
//        view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

//        browser.loadData(Constant.MATHJAX + test.getQuestion(), , "UTF-8");
        view.loadData(Constant.MATHJAX + url, Constant.HTML, Constant.UTF_8 );
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
