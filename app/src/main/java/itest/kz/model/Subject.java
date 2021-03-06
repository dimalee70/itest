package itest.kz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Subject implements Serializable, Parcelable, Comparable, Cloneable
{

    private int isSelected = 0;
    private  int onClickedRecycle = 0;
    private boolean isSubling;

    private boolean isExpand = false;

    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("locale")
    @Expose
    public String locale;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("id_for_generate_test")
    @Expose
    public String idForGenerateTest;

    @SerializedName("color_bg")
    @Expose
    private String colorBg;

    @SerializedName("icon")
    @Expose
    public String icon;

    @SerializedName("image")
    @Expose
    public String image;

    @SerializedName("is_main")
    @Expose
    public boolean isMain;

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("siblings")
    @Expose
    private List<String> sublings;

    @SerializedName("date")
    @Expose
    private Date date;


    public Subject(Long id, String title, String idForGenerateTest, String colorBg, String icon, String image, boolean isMain, String alias, List<String> sublings) {
        this.id = id;
        this.title = title;
        this.idForGenerateTest = idForGenerateTest;
        this.colorBg = colorBg;
        this.icon = icon;
        this.image = image;
        this.isMain = isMain;
        this.alias = alias;
        this.sublings = sublings;
    }

    public Subject(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public int getOnClickedRecycle()
    {
        return onClickedRecycle;
    }

    public void setOnClickedRecycle(int onClickedRecycle)
    {
        this.onClickedRecycle = onClickedRecycle;
    }

    public int getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(int isSelected)
    {
        this.isSelected = isSelected;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<String> getSublings() {
        return sublings;
    }

    public void setSublings(List<String> sublings) {
        this.sublings = sublings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdForGenerateTest() {
        return idForGenerateTest;
    }

    public void setIdForGenerateTest(String idForGenerateTest) {
        this.idForGenerateTest = idForGenerateTest;
    }

    public boolean isExpand()
    {
        return isExpand;
    }

    public void setExpand(boolean expand)
    {
        isExpand = expand;
    }

    public String getColorBg() {
        return colorBg;
    }

    public void setColorBg(String colorBg) {
        this.colorBg = colorBg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public boolean isSubling()
    {
        return isSubling;
    }

    public void setSubling(boolean subling)
    {
        isSubling = subling;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idForGenerateTest='" + idForGenerateTest + '\'' +
                ", colorBg='" + colorBg + '\'' +
                ", icon='" + icon + '\'' +
//                ", subkings = " + sublings.toString() + '\''+
                ", image='" + image + '\'' +
                ", isMain=" + isMain +
                ", isExpand " + isExpand + '\''+
                '}';
    }

    public Subject (Parcel source)
    {
        this.id = source.readLong();
        this.title = source.readString();
        this.colorBg = source.readString();
//        this.nodeId = source.readInt();
//        this.subjectId = source.readInt();
//        this.langId = source.readInt();
//        this.examSubjectId = source.readInt();
//        this.difficultyLevel = source.readInt();
//        this.checked = source.readInt();
//        this.answerType = source.readInt();
//        this.answers = source.readArrayList(Answer.class.getClassLoader());

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(colorBg);
    }

    public static final Parcelable.Creator<Subject> CREATOR = new
            Parcelable.Creator<Subject>(){
                @Override
                public Subject createFromParcel(Parcel source) {
                    return new Subject(source);
                }

                @Override
                public Subject[] newArray(int size) {
                    return new Subject[size];
                }
            };

    @Override
    public int compareTo(Object o)
    {
        Subject compare = (Subject) o;

        if (compare.id == this.id)
        {
            return 0;
        }
        return 1;
    }

    @Override
    public Subject clone() throws CloneNotSupportedException
    {
        Subject clone;
        try {
            clone = (Subject) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); //should not happen
        }

        return clone;
    }
}

