package itest.kz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable, Parcelable
{
    @SerializedName("question_id")
    @Expose
    private Long questionId;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("answer")
    private List<Answer> answers;

    @SerializedName("text_id")
    @Expose
    private Long textId;

    private String text;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Question(Long questionId, String question, List<Answer> answers, Long textId) {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
        this.textId = textId;
    }

    public Long getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(Long questionId)
    {
        this.questionId = questionId;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Answer> answers)
    {
        this.answers = answers;
    }

    public Long getTextId()
    {
        return textId;
    }

    public void setTextId(Long textId)
    {
        this.textId = textId;
    }

    @Override
    public String toString()
    {
        return "Question{" +
                "questionId=" + questionId +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                ", textId=" + textId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Question (Parcel source)
    {
        this.questionId = source.readLong();
        this.question = source.readString();
        this.textId = source.readLong();
        this.text = source.readString();
//        this.nodeId = source.readInt();
//        this.subjectId = source.readInt();
//        this.langId = source.readInt();
//        this.examSubjectId = source.readInt();
//        this.difficultyLevel = source.readInt();
//        this.checked = source.readInt();
//        this.answerType = source.readInt();
        this.answers = source.readArrayList(Answer.class.getClassLoader());

    }



    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(questionId);
        dest.writeString(question);
        if (textId != null) {
            dest.writeLong(textId);
            dest.writeString(text);
        }
//        dest.writeInt(nodeId);
//        dest.writeInt(langId);
//        dest.writeInt(examSubjectId);
//        dest.writeInt(examSubjectId);
//        dest.writeInt(difficultyLevel);
//        dest.writeInt(checked);
//        dest.writeInt(answerType);
        dest.writeList(answers);
    }

    public static final Parcelable.Creator<Question> CREATOR = new
            Parcelable.Creator<Question>(){
                @Override
                public Question createFromParcel(Parcel source) {
                    return new Question(source);
                }

                @Override
                public Question[] newArray(int size) {
                    return new Question[size];
                }
            };
}
