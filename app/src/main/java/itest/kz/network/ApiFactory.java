package itest.kz.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import itest.kz.model.Subject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static itest.kz.util.Constant.BASE_URL;

public class ApiFactory
{
    public static UserService create()
    {
//        01.01.1970
        Gson gson = new GsonBuilder()
                .setDateFormat("dd.mm.yyyy")
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }

    public static SubjectService createSubjectService()
    {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(SubjectService.class);
    }
}
