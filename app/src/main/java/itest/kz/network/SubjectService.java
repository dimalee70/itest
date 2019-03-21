package itest.kz.network;

import java.util.List;

import io.reactivex.Observable;
import itest.kz.model.LectureResponse;
import itest.kz.model.NodesByNode;
import itest.kz.model.NodesBySubject;
import itest.kz.model.SubjectResponce;
import itest.kz.model.Test;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SubjectService
{
    @GET("api/{tag}")
    Observable<SubjectResponce> getSubjects(@Path("tag") String tag,
                                            @Header("Authorization") String authorization,
                                            @Header("Accept") String accept,
                                            @Header("X-Localization") String lang);

    @GET("api/questions/by-math/25")
    Observable<List<Test>> getTests(@Header("Accept") String accept);

    @GET("api/{tag}/node/{id}")
    Observable<NodesBySubject> getNodeBySubject(@Path("tag") String tag,
                                                @Path("id") int id,
                                                @Header("Accept") String accept,
                                                @Header("X-Localization") String lang);

    @GET("api/{tag}/node/{id}")
    Observable<NodesByNode> getNodeByNode(@Path("tag") String tag,
                                          @Path("id") int id,
                                          @Header("Accept") String accept,
                                          @Header("X-Localization") String lang);

    @GET("api/{tag}/lecture/{id}")
    Observable<LectureResponse> getLecture(@Path("tag") String tag,
                                           @Path("id") int id,
                                           @Header("Accept") String accept,
                                           @Header("X-Localization") String lang);
}
