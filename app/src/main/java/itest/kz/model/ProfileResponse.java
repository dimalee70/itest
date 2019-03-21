package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileResponse implements Serializable
{
    @SerializedName("data")
    @Expose
    private Profile profile;

    public ProfileResponse(Profile profile)
    {
        this.profile = profile;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }
}
