package itest.kz.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import itest.kz.model.LectureResponse;
import itest.kz.model.NodeResponse;
import itest.kz.model.NodesByNode;
import itest.kz.model.SaveAnswerResponse;
import itest.kz.model.StatisticSubjectResponce;
import itest.kz.model.SubjectResponce;
import itest.kz.model.Test;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
    Observable<NodeResponse> getNodeBySubject(@Path("tag") String tag,
                                              @Path("id") Long id,
                                              @Header("Accept") String accept,
                                              @Header("X-Localization") String lang,
                                              @Header("Authorization") String accessToken
                                                );

    @GET("api/{tag}/node/{id}")
    Observable<NodesByNode> getNodeByNode(@Path("tag") String tag,
                                          @Path("id") int id,
                                          @Header("Accept") String accept,
                                          @Header("X-Localization") String lang);

    @GET("api/{tag}/lecture/{id}")
    Observable<LectureResponse> getLecture(@Path("tag") String tag,
                                           @Path("id") int id,
                                           @Header("Accept") String accept,
                                           @Header("X-Localization") String lang,
                                           @Header("Authorization") String accessToken);

    @POST("api/test/generate")
    Observable<TestGenerateResponse> getTestGenerate(@Header("Accept") String accept,
                                                     @Header("X-Localization") String lang,
                                                     @Header("Authorization") String accessToken,
                                                     @Body TestGenerateCredentials credentials);

    @GET("api/test/{id}")
    Observable<JsonObject> getQuestions(@Header("Accept") String accept,
                                      @Header("X-Localization") String lang,
                                      @Header("Authorization") String accessToken,
                                      @Path("id") Long id);

    @FormUrlEncoded
    @POST("api/test/{test}/select-answer")
    Observable<SaveAnswerResponse> saveAnswer(@Header("Accept") String accept,
                                              @Header("X-Localization") String lang,
                                              @Header("Authorization") String accessToken,
                                              @Path("test") Long testId,
                                              @Field("question_id") Long questionId,
                                              @Field("answer_id") String answerId);

    @POST("api/test/{test}/finish")
    Observable<TestFinishResponse> finishTest(@Header("Accept") String accept,
                                              @Header("X-Localization") String lang,
                                              @Header("Authorization") String accessToken,
                                              @Path("test") Long testId);

    @GET("api/statistics/ent/subject")
    Observable<StatisticSubjectResponce> getStatisticSubject(@Header("Accept") String accept,
                                                             @Header("X-Localization") String lang,
                                                             @Header("Authorization") String accessToken);
    @GET("api/statistics/ent/lecture")
    Observable<JsonObject> getStatisticLecture(@Header("Accept") String accept,
                                               @Header("X-Localization") String lang,
                                               @Header("Authorization") String accessToken);

    @GET("api/statistics/ent/full")
    Observable<StatisticSubjectResponce> getStatisticFull(@Header("Accept") String accept,
                                                          @Header("X-Localization") String lang,
                                                          @Header("Authorization") String accessToken);

    @GET("api/test/active")
    Observable<JsonObject> getActiveTest(@Header("Accept") String accept,
                                         @Header("X-Localization") String lang,
                                         @Header("Authorization") String accessToken);

    @GET("api/test/{testIdMain}/log-visit")
    Observable<JsonObject> logVisitTest (@Header("Authorization") String accessToken,
                                         @Path("testIdMain") Long testIdMain);

    @GET("api/ent/lecture/{lectureId}/log-visit")
    Observable<JsonObject> logVisitLecture (@Header("Authorization") String accessToken,
                                            @Path("lectureId") int lectureId);

}
