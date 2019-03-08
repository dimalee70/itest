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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import io.github.kexanie.library.MathView;
import itest.kz.R;
import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.util.LETTERS;
import itest.kz.viewmodel.ItemAnswerViewModel;

public class AnswerAdapter extends ArrayAdapter<Answer>
{
    private  Context context;
    private List<Answer> answers;
    private LETTERS [] letters;

    public void setLetter()
    {
        letters = LETTERS.values();
        for(int i = 0; i < answers.size(); i++)
        {
            answers.get(i).setLetter(letters[i].toString());
        }

    }

    public AnswerAdapter(Context context, List<Answer> answers)
    {
        super(context,0, answers);

        this.context = context;
        this.answers = answers;
        setLetter();

        System.out.println(answers.toString());




    }

//    @Override
//    public Answer getItem(int position) {
//        return answers.get(position);
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_answer, parent, false);
        }
        ItemAnswerBinding itemAnswerBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.item_answer, parent, false);
        Answer answer = getItem(position);
        ItemAnswerViewModel itemAnswerViewModel = new ItemAnswerViewModel(context, answer);
        itemAnswerBinding.setAnswer(itemAnswerViewModel);

//        itemAnswerBinding.setAnswer(getItem(position));

//



//        MathView formula_two = (MathView) convertView.findViewById(R.id.formula_two);
//        formula_two.setText(answer.getAnswer());

        WebView webView = (WebView) convertView.findViewById(R.id.answerText);
        TextView letterAnswer = (TextView) convertView.findViewById(R.id.textview1);
        letterAnswer.setText(answer.getLetter());
        webView.getSettings().setJavaScriptEnabled(true);



        webView.loadData( Constant.MATHJAX +
                "<script type=\"text/x-mathjax-config\">\n" +
                " MathJax.Hub.Config({\n" +
                "   showMathMenu: false,\n" +
                "   extensions: [\"tex2jax.js\"],\n" +
                "   jax: [\"input/TeX\", \"output/HTML-CSS\"],\n" +
                " });\n" +
                "</script>"+ answer.getAnswer(), Constant.HTML, Constant.UTF_8);

//        webView.setOnTouchListener(new WebViewClickListener(webView, parent, position));




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

//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        // Get the selected item text from ListView
//        Answer selectedItem = (Answer) parent.getItemAtPosition(position);
//        System.out.println(selectedItem.toString());
//
//        // Display the selected item text on TextView
//    }
}
