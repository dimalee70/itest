package itest.kz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Test implements Serializable, Parcelable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("node_id")
    @Expose
    private int nodeId;

    @SerializedName("subject_id")
    @Expose
    private int subjectId;

    @SerializedName("lang_id")
    @Expose
    private int langId;

    @SerializedName("exam_subject_id")
    @Expose
    private int  examSubjectId;

    @SerializedName("difficulty_level")
    @Expose
    private int  difficultyLevel;

    @SerializedName("checked")
    @Expose
    private int checked;

    @SerializedName("answer_type")
    @Expose
    private int answerType;
//
//    @SerializedName("created_at")
//    @Expose
//    private Date createdAt;
//
//    @SerializedName("updated_at")
//    @Expose
//    private Date updatedAt;
//
//    @SerializedName("deleted_at")
//    @Expose
//    private Date deletedAt;

    @SerializedName("answers")
    @Expose
    private List<Answer> answers;



    public Test(int id, String question, String description, int nodeId, int subjectId, int langId, int examSubjectId, int difficultyLevel, int checked, int answerType, List<Answer> answers) {
        this.id = id;
        this.question = question;
        this.description = description;
        this.nodeId = nodeId;
        this.subjectId = subjectId;
        this.langId = langId;
        this.examSubjectId = examSubjectId;
        this.difficultyLevel = difficultyLevel;
        this.checked = checked;
        this.answerType = answerType;
        this.answers = answers;
    }

//    private Test(Parcelable in)
//    {
//        this.id = in.rea
//    }
//        public Test(int id, String question, String description, int nodeId, int subjectId, int langId, int examSubjectId, int difficultyLevel, int checked, int answerType, Date createdAt, Date updatedAt, Date deletedAt, List<Answer> answers) {
//        this.id = id;
//        this.question = question;
//        this.description = description;
//        this.nodeId = nodeId;
//        this.subjectId = subjectId;
//        this.langId = langId;
//        this.examSubjectId = examSubjectId;
//        this.difficultyLevel = difficultyLevel;
//        this.checked = checked;
//        this.answerType = answerType;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.deletedAt = deletedAt;
//        this.answers = answers;
//    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Date getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(Date deletedAt) {
//        this.deletedAt = deletedAt;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public int getExamSubjectId() {
        return examSubjectId;
    }

    public void setExamSubjectId(int examSubjectId) {
        this.examSubjectId = examSubjectId;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }


    public Test (Parcel source)
    {
        this.id = source.readInt();
        this.question = source.readString();
        this.description = source.readString();
        this.nodeId = source.readInt();
        this.subjectId = source.readInt();
        this.langId = source.readInt();
        this.examSubjectId = source.readInt();
        this.difficultyLevel = source.readInt();
        this.checked = source.readInt();
        this.answerType = source.readInt();
        this.answers = source.readArrayList(Answer.class.getClassLoader());

    }
    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", nodeId=" + nodeId +
                ", subjectId=" + subjectId +
                ", langId=" + langId +
                ", examSubjectId=" + examSubjectId +
                ", difficultyLevel=" + difficultyLevel +
                ", checked=" + checked +
                ", answerType=" + answerType +
                ", answers=" + answers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(description);
        dest.writeInt(nodeId);
        dest.writeInt(langId);
        dest.writeInt(examSubjectId);
        dest.writeInt(examSubjectId);
        dest.writeInt(difficultyLevel);
        dest.writeInt(checked);
        dest.writeInt(answerType);
        dest.writeList(answers);

    }
    public static final Parcelable.Creator<Test> CREATOR = new
            Parcelable.Creator<Test>(){
                @Override
                public Test createFromParcel(Parcel source) {
                    return new Test(source);
                }

                @Override
                public Test[] newArray(int size) {
                    return new Test[size];
                }
            };
}
