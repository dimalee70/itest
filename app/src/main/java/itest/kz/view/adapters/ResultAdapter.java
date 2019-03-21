package itest.kz.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemQuestionsBinding;
import itest.kz.databinding.ItemResultBinding;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.ItemResultViewModel;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;
import itest.kz.viewmodel.ResultViewModel;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder>
{
//

    public interface OnItemClickListener
    {
        void onItemClick(Test test, List<Test> testList);
    }
//    public interface OnItemClickListener
//    {
//        void onItemClick(Test test, int i);
//    }

    private OnItemClickListener listener;
    List<Answer> answerList;
    boolean isStart = false;
    List<Test> testList;
    private Context context;
    private Test test;
    private ItemQuestionsViewModel itemQuestionsViewModel;
//    private ItemQuestionsBinding itemQuestionsBinding;


//    public ResultAdapter (List<Answer> answers)
//    {
//        this.answerList = answers;
////        this.answersAdapterListener = answersAdapterListener;
//    }
    public ResultAdapter(List<Test> testList, Context context)
    {
        this.context = context;
        this.testList = testList;

    }

//    public void set
//

    public void setOnItemListener(ResultAdapter.OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @NonNull
    @Override
    public ResultAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
//        if (!isStart)
//        {
//            Test.num = 1;
//            isStart = true;
//        }
//        }
        ItemResultBinding itemResultBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext())
                        , R.layout.item_result, viewGroup, false );
        return new ResultAdapterViewHolder(itemResultBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapterViewHolder resultAdapterViewHolder, int i)
    {
//        resultAdapterViewHolder.bindAnswer(answerList.get(i));
        this.test = testList.get(i);
        resultAdapterViewHolder.bindTest(test, i);
        this.answerList = testList.get(i).getAnswers();

        RecyclerView recyclerView = resultAdapterViewHolder.recyclerView;
//        RecyclerView.OnItemTouchListener onItemTouchListener
//                = new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent)
//            {
//                return true;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean b) {
//
//            }
//        };
//        recyclerView.addOnItemTouchListener(onItemTouchListener);
//        recyclerView.setOnTouchListener(onItemTouchListener);
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(answerList, context);
        recyclerView.setAdapter(questionsAdapter);

        resultAdapterViewHolder.itemResultBinding.getRoot().findViewById(R.id.cardview_questions)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                tests = testList;
//                System.out.println("Click on carrd");
                Intent intent = new Intent(context, TestActivity.class);
//////        intent.putExtra()
                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, resultAdapterViewHolder
                .getAdapterPosition());
                intent.putExtra(Constant.IS_STARTED_FIRST, false);
                intent.putExtra("list", (ArrayList<Test>) testList);

//////        context.startActivity(TestActivity.fillSelectedSubject(view.etContext(), subject));
//////                StartActivityForResult(intent, 0);
                context.startActivity(intent);
//                listener.onItemClick(test, testList);
            }
        });


        resultAdapterViewHolder.itemResultBinding.cardviewQuestions
                .setCardBackgroundColor((i % 2 == 1)? Color.WHITE : Color.parseColor("#f8f8f8"));
        resultAdapterViewHolder.recyclerView.setBackgroundColor((i % 2 == 1)? Color.WHITE :  Color.parseColor("#f8f8f8"));

        recyclerView.setLayoutManager
                (new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));





//        this.answerList = testList.get(i).getAnswers();

    }
    //    public void setR(RecyclerView recyclerView)
//    {
////        itemQuestionsViewModel = new ItemQuestionsViewModel(this,tests);
////        activityResultBinding.setResult(resultViewModel);
//        QuestionsAdapter questionsAdapter = new QuestionsAdapter(answerList);
//        recyclerView.setAdapter(questionsAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(, LinearLayoutManager.HORIZONTAL
//                , false));
//        resultViewModel = new ResultViewModel(this,test.getAnswers());
////        activityResultBinding.setResult(resultViewModel);
////        ResultAdapter resultAdapter = new ResultAdapter(test.getAnswers());
////        recyclerView.setAdapter(resultAdapter);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//    }


    @Override
    public int getItemCount()
    {
        if (testList != null)
            return testList.size();
        return 0;
//        if (answerList != null)
//            return answerList.size();
//        return 0;
    }
    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
        notifyDataSetChanged();
    }




    public static class ResultAdapterViewHolder extends RecyclerView.ViewHolder
    {
//        ItemSubjectBinding itemSubjectBinding;

        static boolean isStarted = false;
        ItemResultBinding itemResultBinding;
        ItemQuestionsBinding itemQuestionsBinding;
        RecyclerView recyclerView;

        public ResultAdapterViewHolder(ItemResultBinding itemResultBinding) {
            super(itemResultBinding.getRoot());
            this.itemResultBinding = itemResultBinding;
            recyclerView = (RecyclerView) itemResultBinding.listAnswers2;

//            itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    System.out.println("Hello from click");
//                    System.out.println(getAdapterPosition());
//                }
//            });



        }

//        void bindAnswer(Answer answer) {
//            if (itemResultBinding.getResult() == null) {
//                itemResultBinding.setResult(
//                        new ItemResultViewModel(answer, itemView.getContext()));
//            } else {
//                itemResultBinding.getResult().setTest(answer);
//            }
//        }

        void bindTest(Test test, int i)
        {


//            if (!isStarted)
//            {
//                test = test;
//                isStarted = true;
//            }
//            else
//            {
//                for (Answer a : test.getAnswers()) {
//                    if (a.getAnswerResponce() != null)
//                    {
//                        for (int j = 0; j < a.getAnswerResponce().size() - 1; j++)
//                        {
//                            a.getAnswerResponce().set(j, null);
//                        }
////                        for (int r : a.getAnswerResponce()) {
////                            System.out.print(r + "\t");
////                        }
////                    a.setAnswerResponce(null);
//                    }
////                    test.getAnswers();
//                }
////            }

//            itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    System.out.println("Hello from click");
//                    System.out.println(getAdapterPosition());
//                }
//            });

//            itemResultBinding.getRoot().findViewById(R.id.cardview_questions)
//                    .setOnClickListener
//                            (new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v)
//                                {
//                                    new OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(Test test, int i)
//                                        {
//                                            System.out.println("Hello from click");
//                                            System.out.println(i);
//                                        }
//                                    };
//                                }
//                            });

            if (itemResultBinding.getResult() == null)
            {
                itemResultBinding.setResult(
                        new ItemResultViewModel(test, itemView.getContext(),i));
            } else {
                itemResultBinding.getResult().setTest(test);
            }


        }

    }

}

