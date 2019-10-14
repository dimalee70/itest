package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse
{
    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public LoginResponse(String accessToken, String error)
    {
        this.accessToken = accessToken;
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
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
