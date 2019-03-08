package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NodesBySubject implements Serializable
{
    @SerializedName("data")
    @Expose
    private Subject subject;

    @SerializedName("nodes")
    @Expose
    private List<Node> nodes;
//
//    @SerializedName("lectures")
//    @Expose
//    private List<Lecture> lectures;
//
//    public NodesBySubject(Subject subject, List<Node> nodes, List<Lecture> lectures) {
//        this.subject = subject;
//        this.nodes = nodes;
//        this.lectures = lectures;
//    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
//
//    public List<Lecture> getLectures() {
//        return lectures;
//    }
//
//    public void setLectures(List<Lecture> lectures) {
//        this.lectures = lectures;
//    }
}
