package itest.kz.model;

import java.util.List;

import itest.kz.model.Subject;
import itest.kz.model.Test;

public class LectureStatisticResponse implements Comparable, Cloneable
{
    private Subject subject;
    private List<Test> testList;
    private boolean expand = false;

    public LectureStatisticResponse(Subject subject, List<Test> testList)
    {
        this.subject = subject;
        this.testList = testList;
    }

    public boolean isExpand()
    {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public List<Test> getTestList()
    {
        return testList;
    }

    public void setTestList(List<Test> testList)
    {
        this.testList = testList;
    }

    @Override
    public String toString()
    {
        return "LectureStatisticResponse{" +
                "subject=" + subject +
                ", testList=" + testList +
                '}';
    }

    @Override
    public int compareTo(Object o)
    {
        LectureStatisticResponse compare = (LectureStatisticResponse) o;

        if (compare.getSubject().getId() == this.getSubject().getId())
        {
            return 0;
        }
        return 1;

    }

    @Override
    public LectureStatisticResponse clone() throws CloneNotSupportedException
    {
        LectureStatisticResponse clone;
        try {
            clone = (LectureStatisticResponse) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); //should not happen
        }

        return clone;
    }
}
