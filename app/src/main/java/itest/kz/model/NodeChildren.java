package itest.kz.model;

import android.support.annotation.LayoutRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NodeChildren implements Serializable
{
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("lectures_count")
    @Expose
    private int lectureCount;

    public NodeChildren(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public int getLectureCount()
    {
        return lectureCount;
    }

    public void setLectureCount(int lectureCount)
    {
        this.lectureCount = lectureCount;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "NodeChildren{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
