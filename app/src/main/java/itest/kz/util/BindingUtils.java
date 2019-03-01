package itest.kz.util;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import io.reactivex.functions.Action;

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

    @BindingAdapter({"app:loadData"})
    public static void loadData(WebView view, String url)
    {
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

//        browser.loadData(Constant.MATHJAX + test.getQuestion(), , "UTF-8");
        view.loadData(Constant.MATHJAX + url, Constant.HTML, Constant.UTF_8 );
    }

}
