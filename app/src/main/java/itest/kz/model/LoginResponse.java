package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse
{
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public LoginResponse(String accessToken, String errorMessage)
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String access_token)
    {
        this.accessToken = accessToken;
    }

}
