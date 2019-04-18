package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class Answer implements Serializable, Comparable, Cloneable
{
    public int answerResponce = 0;

    private String letter;

    @SerializedName("answer_id")
    @Expose
    private Long answerId;

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("correct")
    @Expose
    private int correct;

    @SerializedName("user_answer")
    @Expose
    private int userAnswer;

    public Answer(int answerResponce, String letter, Long answerId, String answer, int correct)
    {
        this.answerResponce = answerResponce;
        this.letter = letter;
        this.answerId = answerId;
        this.answer = answer;
        this.correct = correct;
    }

    public int getAnswerResponce()
    {
        return answerResponce;
    }

    public void setAnswerResponce(int answerResponce)
    {
        this.answerResponce = answerResponce;
    }

    public String getLetter()
    {
        return letter;
    }

    public void setLetter(String letter)
    {
        this.letter = letter;
    }

    public Long getAnswerId()
    {
        return answerId;
    }

    public void setAnswerId(Long answerId)
    {
        this.answerId = answerId;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public int getCorrect()
    {
        return correct;
    }

    public void setCorrect(int correct)
    {
        this.correct = correct;
    }

    public int getUserAnswer()
    {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer)
    {
        this.userAnswer = userAnswer;
    }

    @Override
    public String toString()
    {
        return "Answer{" +
                "answerResponce=" + answerResponce +
                ", letter='" + letter + '\'' +
                ", answerId=" + answerId +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }

    @Override
    public int compareTo(Object o)
    {
        Answer compare = (Answer) o;

        if (compare.getAnswerId() == this.getAnswerId())
        {
            return 0;
        }
        return 1;
    }

    @Override
    public Answer clone() throws CloneNotSupportedException
    {
        Answer clone;
        try {
            clone = (Answer) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); //should not happen
        }

        return clone;
    }

    //    @SerializedName("id")
//    @Expose
//    private int id;
//
//    @SerializedName("question_id")
//    @Expose
//    private int questionId;
//
//    @SerializedName("answer")
//    @Expose
//    private String answer;
//
//    @SerializedName("correct")
//    @Expose
//    private int correct;
//
//    public Answer(int id, int questionId, String answer, int i, int correct) {
//        this.id = id;
//        this.questionId = questionId;
//        this.answer = answer;
//        this.correct = correct;
//    }
//
//    public String getLetter() {
//        return letter;
//    }
//
//    public void setLetter(String letter) {
//        this.letter = letter;
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
//    public int getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(int questionId) {
//        this.questionId = questionId;
//    }
//
//    public String getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
//
//    public int getCorrect() {
//        return correct;
//    }
//
//    public void setCorrect(int correct) {
//        this.correct = correct;
//    }
//
//    @Override
//    public String toString() {
//        return "Answer{" +
//                "id=" + id +
//                ", questionId=" + questionId +
//                ", answer='" + answer + '\'' +
//                ", correct=" + correct +
//                ", letter="  + letter +
//                '}';
//    }
//
//    public Integer  getAnswerResponce() {
//        return answerResponce;
//    }
//
//    public void setAnswerResponce(Integer answerResponce) {
//        this.answerResponce = answerResponce;
//    }

//    @Override
//    public int compareTo(Answer o)
//    {
//        return this.id - o.getId();
//    }
}
