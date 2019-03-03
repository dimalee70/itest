package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import io.github.kexanie.library.MathView;
import itest.kz.R;
import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.viewmodel.ItemAnswerViewModel;

public class AnswerAdapter extends ArrayAdapter<Answer>
{
    private  Context context;
    private List<Answer> answers;

    public AnswerAdapter(Context context, List<Answer> answers)
    {
        super(context,0, answers);

        this.context = context;
        this.answers = answers;




    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ItemAnswerBinding itemAnswerBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.item_answer, parent, false);
        Answer answer = getItem(position);
        ItemAnswerViewModel itemAnswerViewModel = new ItemAnswerViewModel(context, answer);
        itemAnswerBinding.setAnswer(itemAnswerViewModel);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_answer, parent, false);
        }



//        MathView formula_two = (MathView) convertView.findViewById(R.id.formula_two);
//        formula_two.setText(answer.getAnswer());

        String tex = "<p><span class=\"math-tex\">\\(x\\in(-\\infty;0)\\cup(3;+\\infty)\\)</span></p>";

        WebView webView = (WebView) convertView.findViewById(R.id.answerText);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//
        webView.loadData( Constant.MATHJAX + answer.getAnswer(), Constant.HTML, Constant.UTF_8);



//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

//        TextView textView = (TextView) convertView.findViewById(R.id.textView);
//        textView.setText(Html.fromHtml(Constant.MATHJAX + answer.getAnswer(), Html.FROM_HTML_MODE_COMPACT));

//
//      textView.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>", Html.FROM_HTML_MODE_COMPACT));


//        Constant.MATHJAX + answer.getAnswer()
//
//         webView.loadDataWithBaseURL(null, answer.getAnswer(), Constant.HTML, Constant.UTF_8, null);
//        itemAnswerBinding.getAnswer().answerText.set(answer.getAnswer());
//        WebView webView = (WebView) convertView.findViewById(R.id.answerText);
//
        return convertView;
    }
}
