package itest.kz.util;

import android.os.Bundle;
import android.support.v7.util.DiffUtil;

import java.util.List;

import itest.kz.model.Answer;
import itest.kz.model.Subject;

public class AnswersTestDiffUtilCallback extends DiffUtil.Callback
{

    private List<Answer> newList;
    private List<Answer> oldList;

    public AnswersTestDiffUtilCallback(List<Answer> newList, List<Answer> oldList)
    {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize()
    {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize()
    {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
    {
//        return true;
        return newList.get(newItemPosition).getAnswerResponce() == oldList.get(oldItemPosition).getAnswerResponce();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
    {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Answer newModel = newList.get(newItemPosition);
        Answer oldModel = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

        if (newModel.getAnswerResponce() != (oldModel.getAnswerResponce())) {
            diff.putLong("answer", newModel.getAnswerId());
        }
        if (diff.size() == 0)
        {
            return null;
        }
        return diff;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
