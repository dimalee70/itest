package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class Answer implements Serializable
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("question_id")
    @Expose
    private int questionId;

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("correct")
    @Expose
    private int correct;

    public Answer(int id, int questionId, String answer, int i, int correct) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
