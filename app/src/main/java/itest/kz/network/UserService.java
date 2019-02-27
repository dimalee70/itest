package itest.kz.network;

import android.databinding.ObservableField;

import io.reactivex.Observable;
import itest.kz.model.LoginResponse;
import itest.kz.model.RegisterResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService
{
    @FormUrlEncoded
    @POST("api/register")
    Observable<RegisterResponse>  register(@Field("email") String email,
                                           @Field("password") String password,
                                           @Field("password_confirm") String password_confirm);

//    @FormUrlEncoded
//    @POST
//    Observable<Void> login(@Field("email"),
//                           @Field("password"));

}
