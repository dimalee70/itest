package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContentType implements Serializable
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("tag")
    @Expose
    private String tag;

    @SerializedName("title")
    @Expose
    private String title;

    public ContentType(int id, String tag, String title)
    {
        this.id = id;
        this.tag = tag;
        this.title = title;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
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
        return "ContentType{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
