package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lecture implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("position")
    @Expose
    private int position;

    @SerializedName("has_description")
    @Expose
    private boolean hasDescription;

    @SerializedName("has_questions")
    @Expose
    private boolean hasQuestions;

    @SerializedName("description")
    @Expose
    private String description;

    public Lecture(int id, String title, String format, int position, boolean hasDescription, boolean hasQuestions, String description)
    {
        this.id = id;
        this.title = title;
        this.format = format;
        this.position = position;
        this.hasDescription = hasDescription;
        this.hasQuestions = hasQuestions;
        this.description = description;
    }



    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public boolean isHasDescription()
    {
        return hasDescription;
    }

    public void setHasDescription(boolean hasDescription)
    {
        this.hasDescription = hasDescription;
    }

    public boolean isHasQuestions()
    {
        return hasQuestions;
    }

    public void setHasQuestions(boolean hasQuestions)
    {
        this.hasQuestions = hasQuestions;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "Lecture{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", format='" + format + '\'' +
                ", position=" + position +
                ", hasDescription=" + hasDescription +
                ", hasQuestions=" + hasQuestions +
                '}';
    }

    //    @SerializedName("id")
//    @Expose
//    private int id;
//
//    @SerializedName("id_for_generate_test")
//    @Expose
//    private int idForGenerateTest;
//
//    @SerializedName("is_free")
//    @Expose
//    private int isFree;
//
//    @SerializedName("position")
//    @Expose
//    private String position;
//
//    @SerializedName("title")
//    @Expose
//    private String title;
//
//
//    public Lecture(int id, int idForGenerateTest, int isFree, String position, String title)
//    {
//        this.id = id;
//        this.idForGenerateTest = idForGenerateTest;
//        this.isFree = isFree;
//        this.position = position;
//        this.title = title;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getIdForGenerateTest() {
//        return idForGenerateTest;
//    }
//
//    public void setIdForGenerateTest(int idForGenerateTest) {
//        this.idForGenerateTest = idForGenerateTest;
//    }
//
//    public int getIsFree() {
//        return isFree;
//    }
//
//    public void setIsFree(int isFree) {
//        this.isFree = isFree;
//    }
//
//    public String getPosition() {
//        return position;
//    }
//
//    public void setPosition(String position) {
//        this.position = position;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
}
