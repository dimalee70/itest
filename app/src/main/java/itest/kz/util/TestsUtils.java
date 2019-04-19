package itest.kz.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import itest.kz.model.LectureStatisticResponse;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.Tests;

public class TestsUtils
{
    public static  int getTimeRemaining(int limit, int remaining)
    {
        return  (limit - remaining) * 1000; //* //1000;
    }
    public static ArrayList<Tests> deserializeFromJson(JsonObject jsonObject) throws JSONException {
//        ArrayList<ArrayList<Question>> arrayListsQuestions =
//                new ArrayList<>();

//        System.out.println(accessToken);
        ArrayList<Tests> testsArrayList = new ArrayList<>();
        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
        JSONObject testMain = jsonObject1.getJSONObject("test");
        JSONObject contentType = testMain.getJSONObject("content_type");
        String title = contentType.getString("title");
        JSONObject data = jsonObject1.getJSONObject("data");
        JSONArray tests = data.getJSONArray("tests");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-mm-dd HH:MM:SS")
                .create();
        for (int i = 0; i < tests.length(); i++) {
            JSONObject testItem = tests.getJSONObject(i);

            JSONArray questions = testItem.getJSONArray("questions");
            JSONObject test = testItem.getJSONObject("test");
//            JSONObject subject = testItem.getJSONObject("subject");
            Subject subject = gson.fromJson(testItem.getJSONObject("subject").toString(), Subject.class);
            Long testId = test.getLong("id");
            Test testObject = gson.fromJson(testItem.getJSONObject("test").toString(), Test.class);

            ArrayList<Question> questionsList = new ArrayList<>();
            for (int j = 0; j < questions.length(); j++) {
                Question obj = gson.fromJson(questions.getJSONObject(j).toString(), Question.class);
                if (testItem.has("texts")) {
                    JSONObject texts = testItem.getJSONObject("texts");

                    if (obj.getTextId() != null) {

                        if (texts.has(obj.getTextId().toString())) {

                            JSONObject textsId = texts.getJSONObject
                                    (obj.getTextId().toString());
                            String t = textsId.getString("text");
                            obj.setText(t);
                        }
                    }

                }
                questionsList.add(obj);
            }

            testsArrayList.add(new Tests(questionsList, testId, subject, testObject, title));
        }

        return testsArrayList;
    }

    public static Tests deserializeFromJsonToTests (JsonObject jsonObject) throws JSONException {
        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
        JSONObject data = jsonObject1.getJSONObject("data");

        JSONObject test = jsonObject1.getJSONObject("test");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-mm-dd HH:MM:SS")
                .create();
        Subject subject = gson.fromJson(data.getJSONObject("subject").toString(), Subject.class);
        JSONArray questions = data.getJSONArray("questions");
        Long testId = test.getLong("id");
        Test testObject = gson.fromJson(jsonObject1.getJSONObject("test").toString(), Test.class);
        ArrayList<Question> questionsList = new ArrayList<>();
        for (int j = 0; j < questions.length(); j++) {
            Question obj = gson.fromJson(questions.getJSONObject(j).toString(), Question.class);
            if (data.has("texts")) {
                JSONObject texts = data.getJSONObject("texts");

                if (obj.getTextId() != null) {

                    if (texts.has(obj.getTextId().toString())) {

                        JSONObject textsId = texts.getJSONObject
                                (obj.getTextId().toString());
                        String t = textsId.getString("text");
                        obj.setText(t);
                    }
                }

            }
            questionsList.add(obj);
        }

        return new Tests(questionsList, testId, subject, testObject);
    }

    public static List<LectureStatisticResponse> deserializeFromJsonToLectureStatistic(JsonObject jsonObject) throws JSONException {
        List<LectureStatisticResponse> lectureStatisticResponseList =
                new ArrayList<>();
        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
        JSONObject data = jsonObject1.getJSONObject("data");
        Iterator<String> panelKeys = data.keys();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd.mm.yyyy")
                .create();
        while(panelKeys.hasNext())
        {
            JSONObject itemByKey = data.getJSONObject(panelKeys.next());
            Subject subject = gson.fromJson(itemByKey.getJSONObject("subject").toString(), Subject.class);
            System.out.println("subject");
            System.out.println(subject);
            JSONArray tests = itemByKey.getJSONArray("tests");
            Test test;
            List<Test> testList = new ArrayList<>();
            for (int i = 0; i < tests.length(); i++)
            {
                test = gson.fromJson(tests.getJSONObject(i).toString(), Test.class);
                testList.add(test);
            }
            lectureStatisticResponseList.add(new LectureStatisticResponse(subject, testList));

        }
        return lectureStatisticResponseList;

    }

}
