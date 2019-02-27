package itest.kz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse
{
    @SerializedName("access_token")
    @Expose
    private String access_token;

    public LoginResponse(String access_token, String errorMessage)
    {
        this.access_token = access_token;
    }

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

}
