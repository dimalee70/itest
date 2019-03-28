package itest.kz.model;

import java.util.List;

public class Tests
{
    private List<Question> questions;
    private Long testId;
    private Subject subject;

    public Tests(List<Question> questions, Long testId, Subject subject)
    {
        this.questions = questions;
        this.testId = testId;
        this.subject = subject;
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
}
