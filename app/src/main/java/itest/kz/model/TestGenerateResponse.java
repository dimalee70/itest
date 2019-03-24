package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestGenerateResponse implements Serializable
{
    @SerializedName("data")
    @Expose
    private TestGenerate testGenerate;

    public TestGenerateResponse(TestGenerate testGenerate)
    {
        this.testGenerate = testGenerate;
    }

    public TestGenerate getTestGenerate()
    {
        return testGenerate;
    }

    public void setTestGenerate(TestGenerate testGenerate)
    {
        this.testGenerate = testGenerate;
    }

    @Override
    public String toString()
    {
        return "TestGenerateResponse{" +
                "testGenerate=" + testGenerate +
                '}';
    }
}
