package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StatisticSubjectResponce implements Serializable
{
    @SerializedName("data")
    @Expose
    private List<StatisticSubject> data;

    public StatisticSubjectResponce(List<StatisticSubject> data)
    {
        this.data = data;
    }

    public List<StatisticSubject> getData()
    {
        return data;
    }

    public void setData(List<StatisticSubject> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "StatisticSubjectResponce{" +
                "data=" + data +
                '}';
    }
}
