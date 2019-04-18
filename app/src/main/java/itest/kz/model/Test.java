package itest.kz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Test implements Serializable
{
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("owner_id")
    @Expose
    private int ownerId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("result")
    @Expose
    private TestResult result;

    @SerializedName("created_at")
    @Expose
    private Date createdAt;

    @SerializedName("finished_at")
    @Expose
    private Date finishedAt;

    public Test(Long id, int ownerId, String title, TestResult result, Date createdAt, Date finishedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.result = result;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", title='" + title + '\'' +
                ", result=" + result +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                '}';
    }
}


















//    @SerializedName("id")
//    @Expose
//    private Long id;
//
//    @SerializedName("user_id")
//    @Expose
//    private Long userId;
//
//    @SerializedName("locale")
//    @Expose
//    private String locale;
//
//    @SerializedName("owner_id")
//    @Expose
//    private int ownerId;
//
//    @SerializedName("type")
//    @Expose
//    private String type;
//
//    @SerializedName("parent_id")
//    @Expose
//    private Long parentId;
//
//    @SerializedName("content_type")
//    @Expose
//    private ContentType contentType;
//
//    @SerializedName("result")
//    @Expose
//    private TestResult result;
//
//    @SerializedName("is_finished")
//    @Expose
//    private boolean isFinished;
//
//    public Test(Long id, Long userId, String locale,
//                int ownerId, String type, Long parentId,
//                ContentType contentType, TestResult result, boolean isFinished)
//    {
//        this.id = id;
//        this.userId = userId;
//        this.locale = locale;
//        this.ownerId = ownerId;
//        this.type = type;
//        this.parentId = parentId;
//        this.contentType = contentType;
//        this.result = result;
//        this.isFinished = isFinished;
//    }
//
//    public Long getId()
//    {
//        return id;
//    }
//
//    public void setId(Long id)
//    {
//        this.id = id;
//    }
//
//    public Long getUserId()
//    {
//        return userId;
//    }
//
//    public void setUserId(Long userId)
//    {
//        this.userId = userId;
//    }
//
//    public String getLocale()
//    {
//        return locale;
//    }
//
//    public void setLocale(String locale)
//    {
//        this.locale = locale;
//    }
//
//    public int getOwnerId()
//    {
//        return ownerId;
//    }
//
//    public void setOwnerId(int ownerId)
//    {
//        this.ownerId = ownerId;
//    }
//
//    public String getType()
//    {
//        return type;
//    }
//
//    public void setType(String type)
//    {
//        this.type = type;
//    }
//
//    public Long getParentId()
//    {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId)
//    {
//        this.parentId = parentId;
//    }
//
//    public ContentType getContentType()
//    {
//        return contentType;
//    }
//
//    public void setContentType(ContentType contentType)
//    {
//        this.contentType = contentType;
//    }
//
//    public TestResult getResult()
//    {
//        return result;
//    }
//
//    public void setResult(TestResult result)
//    {
//        this.result = result;
//    }
//
//    public boolean isFinished()
//    {
//        return isFinished;
//    }
//
//    public void setFinished(boolean finished)
//    {
//        isFinished = finished;
//    }
//}

