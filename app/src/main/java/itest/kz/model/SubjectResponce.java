package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectResponce
{
    @SerializedName("data")
    @Expose
    public List<Subject> list;

    public SubjectResponce(List<Subject> list)
    {
        this.list = list;
    }

    public List<Subject> getList()
    {
        return list;
    }

    public void setList(List<Subject> list)
    {
        this.list = list;
    }
}
