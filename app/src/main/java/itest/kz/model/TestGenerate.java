package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestGenerate implements Serializable
{
    @SerializedName("test_id")
    @Expose
    private Long testId;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("owners")
    @Expose
    private List<Long> owners;

    public TestGenerate(Long testId, String type, List<Long> owners)
    {
        this.testId = testId;
        this.type = type;
        this.owners = owners;
    }

    public Long getTestId()
    {
        return testId;
    }

    public void setTestId(Long testId)
    {
        this.testId = testId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public List<Long> getOwners()
    {
        return owners;
    }

    public void setOwners(List<Long> owners)
    {
        this.owners = owners;
    }

    @Override
    public String toString()
    {
        return "TestGenerate{" +
                "testId=" + testId +
                ", type='" + type + '\'' +
                ", owners=" + owners +
                '}';
    }
}
