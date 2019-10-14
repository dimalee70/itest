package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("children")
    @Expose
    private List<NodeChildren> children;

    @SerializedName("lectures")
    @Expose
    private List<Lecture> lectures;

    @SerializedName("lectures_count")
    @Expose
    private int lectureCount;

    public Node(int id, String title, List<NodeChildren> children, List<Lecture> lectures)
    {
        this.id = id;
        this.title = title;
        this.children = children;
        this.lectures = lectures;
    }

    public int getLectureCount()
    {
        return lectureCount;
    }

    public void setLectureCount(int lectureCount)
    {
        this.lectureCount = lectureCount;
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

    public List<NodeChildren> getChildren()
    {
        return children;
    }

    public void setChildren(List<NodeChildren> children)
    {
        this.children = children;
    }

    public List<Lecture> getLectures()
    {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures)
    {
        this.lectures = lectures;
    }

    @Override
    public String toString()
    {
        return "Node{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }

    //    @SerializedName("id_for_generate_test")
//    @Expose
//    private String idForGenerateTest;
//
//
//    public Node(int id, String title, String idForGenerateTest)
//    {
//        this.id = id;
//        this.title = title;
//        this.idForGenerateTest = idForGenerateTest;
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
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getIdForGenerateTest() {
//        return idForGenerateTest;
//    }
//
//    public void setIdForGenerateTest(String idForGenerateTest) {
//        this.idForGenerateTest = idForGenerateTest;
//    }
//
//    @Override
//    public String toString() {
//        return "Node{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", idForGenerateTest='" + idForGenerateTest + '\'' +
//                '}';
//    }
}
