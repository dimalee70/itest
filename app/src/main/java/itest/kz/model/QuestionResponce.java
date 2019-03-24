package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QuestionResponce implements Serializable
{
    @SerializedName("questions")
    @Expose
    private List<Question> question;

//    @SerializedName(value = question)



    public QuestionResponce(List<Question> question)
    {
        this.question = question;
    }

    public List<Question> getQuestion()
    {
        return question;
    }

    public void setQuestion(List<Question> question)
    {
        this.question = question;
    }

    @Override
    public String toString()
    {
        return "QuestionResponce{" +
                "question=" + question +
                '}';
    }
}
