package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.model.Answer;
import itest.kz.util.LETTERS;
import itest.kz.viewmodel.ItemAnswerViewModel;


public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerAdpterViewHolder>
{
    private List<Answer> answerList;
    private Context context;
    private ItemAnswerBinding itemAnswerBinding;
    private LETTERS [] letters;
    private ViewGroup viewGroup;
    private int position;

    public interface OnItemClickListener
    {
        void onItemClick(Answer item, List<Answer> answerList);
    }

//    public interface OnItemTouchListener
//    {
//        void OnItemTouch(Answer item, List<Answer> answerList);
//    }

    private OnItemClickListener listener;
//    private OnItemTouchListener touchListener;

    public void setLetter()
    {
        letters = LETTERS.values();
        for(int i = 0; i < answerList.size(); i++)
        {
            answerList.get(i).setLetter(letters[i].toString());
        }

    }

    @NonNull
    @Override
    public AnswerAdpterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemAnswerBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_answer, viewGroup, false);
        this.viewGroup = viewGroup;
        this.position = i;

        View view = itemAnswerBinding.getRoot();
//        final AnswerAdpterViewHolder answerAdpterViewHolder
//                = new AnswerAdpterViewHolder(itemAnswerBinding);

//        view.setOnClickListener((view) ->
//        {
////           int position = answerAdpterViewHolder.getAdapterPosition();
////            for (int j = 0; j< answerList.size(); j++
////                 ) {
////                if (j != position)
////                    answerList.get(j).setAnswerResponce(null);
//
//
////            }
//            System.out.println("Click Inside Adapter");
//
//        });
        return new AnswerAdpterViewHolder(itemAnswerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdpterViewHolder answerAdpterViewHolder, int i)
    {
//        itemAnswerBinding =
//                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
//                        R.layout.item_answer, viewGroup, false);
//        onCreateViewHolder(viewGroup, position);
        setLetter();
        answerAdpterViewHolder.bindAnswer(answerList.get(i), answerList, listener);

//        if ()
//        ((CardView) answerAdpterViewHolder.itemAnswerBinding.cardview1)
    }


    @Override
    public int getItemCount()
    {
        if (answerList != null)
            return answerList.size();
        return 0;
    }


    public void setOnItemListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }


    public List<Answer> getAnswerList()
    {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList)
    {
        this.answerList = answerList;
        notifyDataSetChanged();

    }

    public static class AnswerAdpterViewHolder extends RecyclerView.ViewHolder
        {


        ItemAnswerViewModel itemAnswerViewModel;
        ItemAnswerBinding itemAnswerBinding;

        public AnswerAdpterViewHolder(ItemAnswerBinding itemAnswerBinding) {
            super(itemAnswerBinding.getRoot());
            this.itemAnswerBinding = itemAnswerBinding;
        }


        void bindAnswer(Answer answer, List<Answer> answerList, final OnItemClickListener listener)
        {
//            itemAnswerBinding.getRoot().findViewById(R.id.answerText).setOnTouchListener
//                    (new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event)
//                        {
//                            if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN)
//                            {
//                                touchListener.OnItemTouch(answer, answerList);
//                                itemAnswerBinding
//                                        .cardview1
//                                        .setCardBackgroundColor(Color.GREEN);
//                                return false;
//                            }
//                            return true;
//                        }
//                    });
//             listener
            itemAnswerBinding.getRoot().findViewById(R.id.cardview1).setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClick(answer, answerList);
                            if (answer.getAnswerResponce() == null)
                            {
                                itemAnswerBinding
                                        .cardview1
                                        .setCardBackgroundColor(Color.WHITE);
                            }
                            else
                            {
                                itemAnswerBinding
                                        .cardview1
                                        .setCardBackgroundColor(Color.GREEN);
                            }

                        }
                    });

            if (itemAnswerBinding.getAnswer() == null) {
                itemAnswerBinding.setAnswer
                        (new ItemAnswerViewModel(itemView.getContext(), answer, answerList));
            } else {
                itemAnswerBinding.getAnswer().setAnswer(answer);
            }
            if (answer.getAnswerResponce() != null)
            {
                itemAnswerBinding.cardview1
                        .setCardBackgroundColor(Color.GREEN);
//                notify();

            }
            else
                {
                itemAnswerBinding.cardview1
                        .setCardBackgroundColor(Color.WHITE);
//                answer.setAnswerResponce(null);
//                notify();
            }
        }


//            @Override
//            public void update(Observable o, Object arg)
//            {
//
//            }
        }


}

//public class AnswerAdapter extends ArrayAdapter<Answer>
//{
//    private  Context context;
//    private List<Answer> answers;
//    private LETTERS [] letters;
//

//
//    public AnswerAdapter(Context context, List<Answer> answers)
//    {
//        super(context,0, answers);
//
//        this.context = context;
//        this.answers = answers;
//        setLetter();
//
////        System.out.println(answers.toString());
//
//
//
//
//    }
//
////    @Override
////    public Answer getItem(int position) {
////        return answers.get(position);
////    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
////        if (convertView == null) {
////            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_answer, parent, false);
////        }
//        ItemAnswerBinding itemAnswerBinding = DataBindingUtil
//                .inflate(LayoutInflater.from(context), R.layout.item_answer, parent, false);
//        Answer answer = getItem(position);
//        ItemAnswerViewModel itemAnswerViewModel = new ItemAnswerViewModel(context, answer);
//        itemAnswerBinding.setAnswer(itemAnswerViewModel);
//
////        itemAnswerBinding.setAnswer(getItem(position));
//
////
//
//
////        WebView webView = (WebView) convertView.findViewById(R.id.answerText);
////        CardView cardView = (CardView) convertView.findViewById()
////        TextView letterAnswer = (TextView) convertView.findViewById(R.id.textview1);
////        letterAnswer.setText(answer.getLetter());
////        webView.getSettings().setJavaScriptEnabled(true);
////
////
////
////        webView.loadData( Constant.MATHJAX +
////                "<script type=\"text/x-mathjax-config\">\n" +
////                " MathJax.Hub.Config({\n" +
////                "   showMathMenu: false,\n" +
////                "   extensions: [\"tex2jax.js\"],\n" +
////                "   jax: [\"input/TeX\", \"output/HTML-CSS\"],\n" +
////                " });\n" +
////                "</script>"+ answer.getAnswer(), Constant.HTML, Constant.UTF_8);
////
////
//        return itemAnswerBinding.getRoot();
//    }
//
////    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////        // Get the selected item text from ListView
////        Answer selectedItem = (Answer) parent.getItemAtPosition(position);
////        System.out.println(selectedItem.toString());
////
////        // Display the selected item text on TextView
////    }
//}
