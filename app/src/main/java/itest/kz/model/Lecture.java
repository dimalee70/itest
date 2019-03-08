package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lecture implements Serializable
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_for_generate_test")
    @Expose
    private int idForGenerateTest;

    @SerializedName("is_free")
    @Expose
    private int isFree;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("title")
    @Expose
    private String title;


    public Lecture(int id, int idForGenerateTest, int isFree, String position, String title)
    {
        this.id = id;
        this.idForGenerateTest = idForGenerateTest;
        this.isFree = isFree;
        this.position = position;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdForGenerateTest() {
        return idForGenerateTest;
    }

    public void setIdForGenerateTest(int idForGenerateTest) {
        this.idForGenerateTest = idForGenerateTest;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
