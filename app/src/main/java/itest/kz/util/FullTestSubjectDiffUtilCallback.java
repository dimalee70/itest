package itest.kz.util;

import android.os.Bundle;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

import itest.kz.model.Subject;

public class FullTestSubjectDiffUtilCallback extends DiffUtil.Callback
{
    private List<Subject> newList;
    private List<Subject> oldList;

    public FullTestSubjectDiffUtilCallback(List<Subject> newList, List<Subject> oldList)
    {
        this.newList = newList;
        this.oldList = oldList;
    }



    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
    {
        return newList.get(newItemPosition).getOnClickedRecycle() == oldList.get(oldItemPosition).getOnClickedRecycle();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Subject newModel = newList.get(newItemPosition);
        Subject oldModel = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

        if (newModel.getOnClickedRecycle() != (oldModel.getOnClickedRecycle())) {
            diff.putInt("price", newModel.getOnClickedRecycle());
        }
        if (diff.size() == 0) {
            return null;
        }
        return diff;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
