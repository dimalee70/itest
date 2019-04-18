package itest.kz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tests implements Serializable
{
    private List<Question> questions;
    private Long testId;
    private Subject subject;
//    private TestResult testResult;

    private Test test;

    public Tests()
    {
        questions = new ArrayList<>();
    }

    public Tests(List<Question> questions, Long testId, Subject subject, Test test)
    {
        this.questions = questions;
        this.testId = testId;
        this.subject = subject;
        this.test = test;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    public Long getTestId()
    {
        return testId;
    }

    public void setTestId(Long testId)
    {
        this.testId = testId;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Tests{" +
                "questions=" + questions +
                ", testId=" + testId +
                ", subject=" + subject +
                '}';
    }
}
