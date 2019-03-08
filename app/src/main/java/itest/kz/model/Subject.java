package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Subject implements Serializable
{
    @SerializedName("id")
    @Expose
    public int id;

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


    public Subject(int id, String title, String idForGenerateTest, String colorBg, String icon, String image, boolean isMain, String alias, List<String> sublings) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idForGenerateTest='" + idForGenerateTest + '\'' +
                ", colorBg='" + colorBg + '\'' +
                ", icon='" + icon + '\'' +
                ", image='" + image + '\'' +
                ", isMain=" + isMain +
                '}';
    }
}

