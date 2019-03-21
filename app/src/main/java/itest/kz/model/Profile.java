package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Profile implements Serializable
{
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("firstname")
    @Expose
    private String firstName;

    @SerializedName("surname")
    @Expose
    private String surName;

    @SerializedName("lastname")
    @Expose
    private String lastName;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("born_date")
    @Expose
    private Date bornDate;

    public Profile(Long id, String login, String email, String firstName, String surName, String lastName, String avatarUrl, Date bornDate)
    {
        this.id = id;
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.lastName = lastName;
        this.avatarUrl = avatarUrl;
        this.bornDate = bornDate;
    }

    public Date getBornDate()
    {
        return bornDate;
    }

    public void setBornDate(Date bornDate)
    {
        this.bornDate = bornDate;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurName()
    {
        return surName;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", bornDate=" + bornDate +
                '}';
    }
}
