package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.util.AnswersTestDiffUtilCallback;
import itest.kz.util.Constant;
import itest.kz.util.FullTestSubjectDiffUtilCallback;
import itest.kz.util.LETTERS;
import itest.kz.viewmodel.ItemAnswerViewModel;
import itest.kz.viewmodel.ItemFullEntViewModel;


public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerAdpterViewHolder>
{
    private List<Answer> answerList;
    private Context context;
    private ItemAnswerBinding itemAnswerBinding;
    private LETTERS [] letters;
    private ViewGroup viewGroup;
    private int position;
    public String resultTag;
    private boolean isFirst = true;

    public interface OnItemClickListener
    {
        void onItemClick(Answer item, int position) throws CloneNotSupportedException;
    }

//    public interface OnItemTouchListener
//    {
//        void OnItemTouch(Answer item, List<Answer> answerList);
//    }

    public AnswerAdapter(List<Answer> answerList, String resultTag)
    {
        this.answerList = answerList;
        this.resultTag = resultTag;
    }

    public AnswerAdapter(List<Answer> answerList)
    {
        this.answerList = answerList;
    }

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
//        if (isFirst)
//        {
            setLetter();
            if (resultTag == null)
            {
                answerAdpterViewHolder.bindAnswer(answerList.get(i), i, listener);
//                if (resultTag == null)
//                {
                    itemAnswerBinding.cardview1.setOnClickListener
                            (new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        listener.onItemClick(answerList.get(i), i);
                                    } catch (CloneNotSupportedException e) {
                                        e.printStackTrace();
                                    }
//                            if (answer.getAnswerResponce() == null)
//                            {
//                                itemAnswerBinding
//                                        .cardview1
//                                        .setCardBackgroundColor(Color.WHITE);
//                            }
//                            else
//                            {
//                                itemAnswerBinding
//                                        .cardview1
//                                        .setCardBackgroundColor(Color.GREEN);
//                            }

                                }
                            });
//                }
            }


            else
            {
                answerAdpterViewHolder.bindAnswer(answerList.get(i), i, resultTag, listener);
            }
//            isFirst = false;
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdpterViewHolder answerAdpterViewHolder,
                                 int position,
                                 @NonNull List<Object> payloads)
    {
        if (payloads.isEmpty())
            super.onBindViewHolder(answerAdpterViewHolder, position, payloads);
        else
        {

            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals("answer"))
                {
                    itemAnswerBinding.cardview1
                            .setCardBackgroundColor(
                                    Color.GREEN);
                    itemAnswerBinding.textview1.setTextColor(Color.WHITE);
                }
                else
                {
                    itemAnswerBinding.cardview1
                            .setCardBackgroundColor(
                                    Color.WHITE);
                    itemAnswerBinding.textview1.setTextColor(Color.BLACK);
                }
            }

//            fullTestSubjectAdapterViewHolder.itemFullSubjectBinding.entStartCardvi
//            if (subjectList.get(position).getIsSelected() == 1)
//            {
//                itemFullSubjectBinding.entStartCardview
//                        .setCardBackgroundColor(
//                                Color.parseColor(Constant.colorSelectedSubjectOnEnt));
//                itemFullSubjectBinding.titleSubjectText.setTextColor(Color.WHITE);
//
//            }
        }
    }

    @Override
    public int getItemCount()
    {
        if (answerList != null)
            return answerList.size();
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
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


            public void bindAnswer(Answer answer, int position, String resultTag, final OnItemClickListener listener)
            {
                if(itemAnswerBinding.getAnswer() == null)
                {
                    itemAnswerBinding.setAnswer(
                            new ItemAnswerViewModel(itemView.getContext(),answer));
                }
                else
                {
                    itemAnswerBinding.getAnswer().setAnswer(answer);

                }


                if (answer.getUserAnswer() == 1)
                {
                    if (answer.getCorrect() == 1)
                    {
                        itemAnswerBinding.cardview1
                                .setCardBackgroundColor(
                                        Color.parseColor("#68DA78")

//                                Color.GREEN
                                );
                    }
                    else
                    {
                        itemAnswerBinding.cardview1
                                .setCardBackgroundColor(
                                        Color.parseColor("#F75C5B")
//                                Color.GREEN
                                );
                    }

                    itemAnswerBinding.textview1.setTextColor
                            (Color.WHITE);
                }
                else
                {
                    itemAnswerBinding.cardview1
                            .setCardBackgroundColor(
                                    Color.WHITE);
                    itemAnswerBinding.textview1.setTextColor(Color.BLACK);
                }

                if (answer.getCorrect() == 1)
                {
                    itemAnswerBinding.cardview1
                            .setCardBackgroundColor(
                                    Color.parseColor("#68DA78")
//                                    Color.GREEN
                            );
//                    System.out.println("inside if ");
                    itemAnswerBinding.textview1.setTextColor
                            (Color.WHITE);
                }

//                if (resultTag == null)
//                {
//                    itemAnswerBinding.cardview1.setOnClickListener
//                            (new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    try {
//                                        listener.onItemClick(answer, getAdapterPosition());
//                                    } catch (CloneNotSupportedException e) {
//                                        e.printStackTrace();
//                                    }
////                            if (answer.getAnswerResponce() == null)
////                            {
////                                itemAnswerBinding
////                                        .cardview1
////                                        .setCardBackgroundColor(Color.WHITE);
////                            }
////                            else
////                            {
////                                itemAnswerBinding
////                                        .cardview1
////                                        .setCardBackgroundColor(Color.GREEN);
////                            }
//
//                                }
//                            });
//                }

//            if (itemAnswerBinding.getAnswer() == null) {
//                itemAnswerBinding.setAnswer
//                        (new ItemAnswerViewModel(itemView.getContext(), answer, answerList));
//            } else {
//                itemAnswerBinding.getAnswer().setAnswer(answer);
//            }
//
//
//            if (answer.getAnswerResponce() != null)
//            {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(Color.GREEN);
////                notify();
//
//            }
//            else
//                {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(Color.WHITE);
////                answer.setAnswerResponce(null);
////                notify();
//            }
            }



        public void bindAnswer(Answer answer, int position, final OnItemClickListener listener)
        {
            if(itemAnswerBinding.getAnswer() == null)
            {
                itemAnswerBinding.setAnswer(
                        new ItemAnswerViewModel(itemView.getContext(),answer));
            }
            else
            {
                itemAnswerBinding.getAnswer().setAnswer(answer);

            }
            if (answer.getUserAnswer() == 1)
            {
                itemAnswerBinding.cardview1
                        .setCardBackgroundColor(
                                Color.parseColor("#ff2daafc")
//                                Color.GREEN
                        );
                itemAnswerBinding.textview1.setTextColor
                        (Color.WHITE);
            }
            else
            {
                itemAnswerBinding.cardview1
                        .setCardBackgroundColor(
                                Color.WHITE);
                itemAnswerBinding.textview1.setTextColor(Color.BLACK);
            }
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
            itemAnswerBinding.cardview1.setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            try {
                                listener.onItemClick(answer, getAdapterPosition());
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
//                            if (answer.getAnswerResponce() == null)
//                            {
//                                itemAnswerBinding
//                                        .cardview1
//                                        .setCardBackgroundColor(Color.WHITE);
//                            }
//                            else
//                            {
//                                itemAnswerBinding
//                                        .cardview1
//                                        .setCardBackgroundColor(Color.GREEN);
//                            }

                        }
                    });

//            if (itemAnswerBinding.getAnswer() == null) {
//                itemAnswerBinding.setAnswer
//                        (new ItemAnswerViewModel(itemView.getContext(), answer, answerList));
//            } else {
//                itemAnswerBinding.getAnswer().setAnswer(answer);
//            }
//
//
//            if (answer.getAnswerResponce() != null)
//            {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(Color.GREEN);
////                notify();
//
//            }
//            else
//                {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(Color.WHITE);
////                answer.setAnswerResponce(null);
////                notify();
//            }
        }


//            @Override
//            public void update(Observable o, Object arg)
//            {
//
//            }
        }

    public void setData(List<Answer> newData)
    {

//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AnswersTestDiffUtilCallback(newData, answerList));


//        findViewHolderForAdapterPosition(pos);
        answerList.clear();
        answerList.addAll(newData);

//        for (int i = 0; i < answerList.size(); i++)
//        {
//            if (answerList.get(i).getUserAnswer() == 1)
//            {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(
//                                Color.parseColor("#ff2daafc")
////                                Color.GREEN
//                        );
//                itemAnswerBinding.textview1.setTextColor
//                        (Color.WHITE);
//            }
//            else
//            {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(
//                                Color.WHITE);
//                itemAnswerBinding.textview1.setTextColor(Color.BLACK);
//            }
//        }


//        int t = 0;
//        for (int i = 0; i < answerList.size(); i++)
//        {
//            if (answerList.get(i).getUserAnswer() == 1)
//            {
//                t = i;
//                notifyItemChanged(i);
//            }
//
//        }
//        diffResult.dispatchUpdatesTo(this);
//        notifyDataSetChanged();

    }

    public List<Answer> getData()
    {
        return answerList;
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
