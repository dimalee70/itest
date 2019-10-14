package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NodesByNode implements Serializable
{
    @SerializedName("lectures")
    @Expose
    private List<Lecture> lectures;

    public List<Lecture> getLectures()
    {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures)
    {
        this.lectures = lectures;
    }
}
