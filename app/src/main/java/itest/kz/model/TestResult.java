package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestResult implements Serializable
{
    @SerializedName("points")
    @Expose
    private int points;

    @SerializedName("all")
    @Expose
    private int all;

    @SerializedName("percent")
    @Expose
    private double percent;

    @SerializedName("time")
    @Expose
    private int time;

    public TestResult(int points, int all, double percent, int time)
    {
        this.points = points;
        this.all = all;
        this.percent = percent;
        this.time = time;
    }

    public TestResult(int points, int all, double percent)
    {
        this.points = points;
        this.all = all;
        this.percent = percent;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getAll()
    {
        return all;
    }

    public void setAll(int all)
    {
        this.all = all;
    }

    public double getPercent()
    {
        return percent;
    }

    public void setPercent(double percent)
    {
        this.percent = percent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
