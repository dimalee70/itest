package itest.kz.network;

import android.databinding.ObservableField;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import itest.kz.model.LoginResponse;
import itest.kz.model.PasswordChangeResponce;
import itest.kz.model.ProfileInfo;
import itest.kz.model.ProfileResponse;
import itest.kz.model.RegisterResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService
{
    @FormUrlEncoded
    @POST("api/register")
    Observable<RegisterResponse>  register(@Header("X-Localization") String lang,
                                           @Field("email") String email,
                                           @Field("password") String password,
                                           @Field("password_confirm") String password_confirm);

    @FormUrlEncoded
    @POST("api/login")
    Observable<LoginResponse> login(@Header("X-Localization") String lang,
                                    @Field("login") String email,
                                    @Field("password")String password);

    @GET("api/profile")
    Observable<ProfileResponse> getProfile(@Header("X-Localization") String lang);

    @Multipart
    @POST("api/avatar-update")
    Observable<ProfileResponse> updateAvatar(@Header("X-Localization") String lang,
                                             @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("api/profile")
    Observable<ProfileResponse> setProfile(@Header("X-Localization") String lang,
                                           @Field("firstname") String firstname,
                                           @Field("surname") String surname,
                                           @Field("born_date") String bornDate,
                                           @Field("login") String login,
                                           @Field("email") String email
                                           );

    @FormUrlEncoded
    @POST("api/change-password")
    Observable<PasswordChangeResponce> changePassword(@Header("X-Localization") String lang,
                                                      @Field("old_password") String password,
                                                      @Field("new_password") String newPassword,
                                                      @Field("new_password_confirmation") String newPasswordConfirm
                                                      );

    @FormUrlEncoded
    @POST("api/reset-password")
    Observable<JsonObject> resetPassword(@Header("X-Localization") String lang,
                                         @Field("email") String email);
}
