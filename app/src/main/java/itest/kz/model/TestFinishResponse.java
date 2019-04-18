package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestFinishResponse implements Serializable
{
    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("reload")
    @Expose
    private boolean reload;

    @SerializedName("result")
    @Expose
    private  TestResult result;

    public TestFinishResponse(String success, boolean reload, TestResult result) {
        this.success = success;
        this.reload = reload;
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public boolean isReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }
}
