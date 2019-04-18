package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemAnswerCheckBinding;
import itest.kz.model.Answer;
import itest.kz.viewmodel.ItemAnswerCheckViewModel;

public class AnswerCheckAdapter extends RecyclerView.Adapter<AnswerCheckAdapter.AnswerCheckAdpterViewHolder>
{
    private List<Answer> answerList;
    private Context context;
    private ItemAnswerCheckBinding itemAnswerCheckBinding;

    public AnswerCheckAdapter(List<Answer> answerList)
    {
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public AnswerCheckAdpterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemAnswerCheckBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_answer_check, viewGroup, false);


//        View view = itemAnswerBinding.getRoot();

        return new AnswerCheckAdpterViewHolder(itemAnswerCheckBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerCheckAdpterViewHolder answerCheckAdpterViewHolder, int i)
    {
        answerCheckAdpterViewHolder.bindAnswer(answerList.get(i), i);
    }

    @Override
    public int getItemCount()
    {
        if (answerList != null)
            return answerList.size();
        return 0;
    }

    public static class AnswerCheckAdpterViewHolder extends RecyclerView.ViewHolder
    {

        ItemAnswerCheckBinding itemAnswerCheckBinding;
        ItemAnswerCheckViewModel itemAnswerCheckViewModel;

        public AnswerCheckAdpterViewHolder(ItemAnswerCheckBinding itemAnswerCheckBinding) {
            super(itemAnswerCheckBinding.getRoot());
            this.itemAnswerCheckBinding = itemAnswerCheckBinding;
        }


        public void bindAnswer(Answer answer, int position)
        {
            if(itemAnswerCheckBinding.getAnswer() == null)
            {
                itemAnswerCheckBinding.setAnswer(
                        new ItemAnswerCheckViewModel(itemView.getContext(),answer));
            }
            else
            {
                itemAnswerCheckBinding.getAnswer().setAnswer(answer);

            }


//            if (answer.getUserAnswer() == 1)
//            {
//                if (answer.getCorrect() == 1)
//                {
//                    itemAnswerBinding.cardview1
//                            .setCardBackgroundColor(
//                                    Color.parseColor("#ff68da78")
////                                Color.GREEN
//                            );
//                }
//                else
//                {
//                    itemAnswerBinding.cardview1
//                            .setCardBackgroundColor(
//                                    Color.parseColor("#ffff6969")
////                                Color.GREEN
//                            );
//                }
//
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
//
//            if (answer.getCorrect() == 1)
//            {
//                itemAnswerBinding.cardview1
//                        .setCardBackgroundColor(
////                                    Color.parseColor("#ff68da78")
//                                Color.GREEN
//                        );
////                    System.out.println("inside if ");
//                itemAnswerBinding.textview1.setTextColor
//                        (Color.WHITE);
//            }
//
//
//            itemAnswerBinding.cardview1.setOnClickListener
//                    (new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v)
//                        {
//                            try {
//                                listener.onItemClick(answer, getAdapterPosition());
//                            } catch (CloneNotSupportedException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    });

        }

        }

        public List<Answer> getData()
        {
            return answerList;
        }


    }



//public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerAdpterViewHolder>
//
//
//
//    @Override
//    public void onBindViewHolder(@NonNull AnswerAdpterViewHolder answerAdpterViewHolder, int i)
//    {
////        itemAnswerBinding =
////                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
////                        R.layout.item_answer, viewGroup, false);
////        onCreateViewHolder(viewGroup, position);
//        setLetter();
//        if (resultTag == null)
//            answerAdpterViewHolder.bindAnswer(answerList.get(i),i,  listener);
//        else
//        {
//            answerAdpterViewHolder.bindAnswer(answerList.get(i), i, resultTag, listener);
//        }
//
////        if ()
////        ((CardView) answerAdpterViewHolder.itemAnswerBinding.cardview1)
//    }
//
//
//
//    @Override
//    public int getItemCount()
//    {
//        if (answerList != null)
//            return answerList.size();
//        return 0;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//    public void setOnItemListener(OnItemClickListener listener)
//    {
//        this.listener = listener;
//    }
//
//
//    public List<Answer> getAnswerList()
//    {
//        return answerList;
//    }
//
//    public void setAnswerList(List<Answer> answerList)
//    {
//        this.answerList = answerList;
//        notifyDataSetChanged();
//
//    }



