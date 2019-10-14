package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StatisticSubject implements Serializable
{
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("owner_id")
    @Expose
    private int ownerId;

    @SerializedName("result")
    @Expose
    private TestResult result;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("finished_at")
    @Expose
    private String finishedAt;

    @SerializedName("children")
    @Expose
    private List<Subject> children;



    public StatisticSubject(Long id, String title, int ownerId, TestResult result, String createdAt, String finishedAt)
    {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
        this.result = result;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
    }

    public List<Subject> getChildren() {
        return children;
    }

    public void setChildren(List<Subject> children) {
        this.children = children;
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

    public int getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(int ownerId)
    {
        this.ownerId = ownerId;
    }

    public TestResult getResult()
    {
        return result;
    }

    public void setResult(TestResult result)
    {
        this.result = result;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getFinishedAt()
    {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt)
    {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString()
    {
        return "StatisticSubject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerId +
                ", result=" + result +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                '}';
    }

}
