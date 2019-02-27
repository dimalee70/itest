package itest.kz.network;

import java.util.List;

import io.reactivex.Observable;
import itest.kz.model.SubjectResponce;
import itest.kz.model.Test;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SubjectService
{
    @GET("api/attestation")
    Observable<SubjectResponce> getSubjects(@Header("Authorization") String authorization,
                                            @Header("Accept") String accept,
                                            @Header("X-Localization") String lang);

    @GET("api/questions/byMath/25")
    Observable<List<Test>> getTests(@Header("Accept") String accept);
}
