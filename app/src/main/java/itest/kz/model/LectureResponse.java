package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LectureResponse implements Serializable
{
    @SerializedName("data")
    @Expose
    private Lecture lecture;

    public LectureResponse(Lecture lecture)
    {
        this.lecture = lecture;
    }

    public Lecture getLecture()
    {
        return lecture;
    }

    public void setLecture(Lecture lecture)
    {
        this.lecture = lecture;
    }

    @Override
    public String toString()
    {
        return "LectureResponse{" +
                "lecture=" + lecture +
                '}';
    }

    //    @SerializedName("description")
//    @Expose
//    private String description;
//
//    public LectureResponse(Lecture lecture, String description)
//    {
//        this.lecture = lecture;
//        this.description = description;
//    }
//
//    public Lecture getLecture()
//    {
//        return lecture;
//    }
//
//    public void setLecture(Lecture lecture)
//    {
//        this.lecture = lecture;
//    }
//
//    public String getDescription()
//    {
//        return description;
//    }
//
//    public void setDescription(String description)
//    {
//        this.description = description;
//    }
//
//    @Override
//    public String toString() {
//        return "LectureResponse{" +
//                "lecture=" + lecture +
//                ", description='" + description + '\'' +
//                '}';
//    }
}
