package itest.kz.util;

public interface Constant
{
    public final String MY_START = "MY_START";

    public final String STATISTIC_TAG = "STATISTIC_TAG";

    public final String SERVER_ERROR_ALERT_KZ = "Белгісіз қате. Тағы да бір рет көріңіз";

    public final String SERVER_ERROR_ALERT_RU = "Проблемы с сервером. Попробуйте еще раз";

    public final String hasActiveTest = "test";

    public final  String ANSWER_CHECK_LIST = "ANSWER_CHECK_LIST";

    public final  String QUESTION_FOR_CHECK = "QUESTION_FOR_CHECK";

    public final  String RESULT_TAG = "RESULT_TAG";

    public  final  String TEST_FINISH_RESPONSE = "TEST_FINISH_RESPONSE";

    public final  String CURRENT_TIME = "CURRENT_TIME";

    public  final  String CURRENT_POSITION_SUBJECT = "CURRENT_POSITION_SUBJECT";

    public final  String CURRENT_POSITION_TEST = "CURRENT_POSITION_TEST";

    public final String SUBJECT_LIST = "SUBJECT_LIST";

    public  final String SELECTED_RESULT = "SELECTED_RESULT";

    public final  String TEST_MAIN_ID = "TEST_MAIN_ID";

    public  final  String ARRAYLISTTEST = "ARRAYLISTTEST";

    public final String TYPE = "type";

    public final String TYPEFULLTEST = "full";

    public final String TYPESUBJECTTEST = "subject";

    public final String TYPELECTURETEST = "lecture";

    public final String colorSelectedSubjectOnEnt = "#FF37ACF9";

    public final String MULTIPART = "multipart/form-data";

    public static String camera_permission_message   = "Access to camera is needed";

    public static final int HTTP_NO_CONTENT       = 204;

    public static final int CAMERA_REQUEST_CODE   = 999;

    public static final int LOCATION_REQUEST_CODE = 998;

    public static final int SPLASH_DISPLAY_LENGTH = 500;

    public final String PROFILE = "PROFILE";

    public final String EMPTY_PHOTO = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-FDni2p-OnoELD2aN9f7JLkhqv4Dc02eTIjzlzFbhePuPVbDNbA";

    public final  String MY_PREF = "MY_PREF";

    public final String MY_LANG = "MY_LANG";

    public final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public final String LANG = "LANG";

    public final  String KZ = "kz";

    public final String RU = "ru";

    public final String IS_STARTED_FIRST = "IS_STARTED_FIRST";

    public final String SELECTED_TEST_POSITION_ID = "SELECTED_TEST_POSITION_ID";

    public final  String BASE_URL = "https://itest.kz/";

    public final String ACCEPT = "application/json";

    public final String ATTESTATION = "attestation";

    public final String SELECTED_LECTURE_RESPONSE = "SELECTED_LECTURE_RESPONSE";

    public final String ENT = "ent";

    public final String HTML = "text/html; charset=utf-8";

    public final String UTF_8 = "UTF-8";

    public final String SELECTED_SUBJECT = "SELECTED_SUBJECT";

    public final String SELECTED_NODE = "SELECTED_NODE";

    public final String MATHJAX = "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-MML-AM_CHTML' async></script>" +
            "<script type=\"text/x-mathjax-config\">\n" +
            "MathJax.Hub.Config({\n" +
            "  CommonHTML: { linebreaks: { automatic: true, width: \"90% container\"  } },\n" +
            "  \"HTML-CSS\": { linebreaks: { automatic: true, width: \"90% container\"  } },\n" +
            "         SVG: { linebreaks: { automatic: true, width: \"90% container\"  } }\n" +
            "});\n" +
            "</script>";

    public final String ACCESSTOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjFmN2JiNTU5MjViZDI5MjhkN2Q0Mzc0YTBjOWNkZWZhOTFiZTc1ZjE1N2IzZjRhZGRiZDhmZjU4OWMyYjgyYWQ5ZTMzOTdjZjkxNWUwMmJkIn0.eyJhdWQiOiIxIiwianRpIjoiMWY3YmI1NTkyNWJkMjkyOGQ3ZDQzNzRhMGM5Y2RlZmE5MWJlNzVmMTU3YjNmNGFkZGJkOGZmNTg5YzJiODJhZDllMzM5N2NmOTE1ZTAyYmQiLCJpYXQiOjE1NTU0ODkzNzMsIm5iZiI6MTU1NTQ4OTM3MywiZXhwIjoxNTg3MTExNzczLCJzdWIiOiIxMjA2MjU4Iiwic2NvcGVzIjpbXX0.cgecoiJj6K68tkp42l0tesx_qVseJUWo_fE7lz5ZwB6Znkk78AXyI77dbGfUGR43q-ijGwhvjeUAiPIgqpoxoewJ7yk_Ckv6amwPDnGSh8RO_8kbxZUwVobBqtI-Bs9yDeyZ7wkINHNt410zE0_oDt-hdN8NDhXzkotC3eYXaLOz-bMK3LQOpFhj136CIDvbshtu4AB4GZLB7voFRqYtmS2ge_a-HRSiQcX44GLZ8rbmzx78oMAFIQ8ybuLZfL1XAzILu5qa7jwjsoEHv-GDneiG8EjS0fPgfzj3gwKssGvFL3YRUWvBMEamaII08Ku4CV5P8nTNd8osI9ZOlDX2lT4-WK7TPu5L7qn6IGmGkDuCTd9ePkSj44iqW7ZRJB1CPo8toyiQa57E8XfWzZD2AtraUdpYvfjLQGvxyAbRGZabD_e7TEBArzaCC5Z6eGLhQ0_fhxDNUYH4fGzwXTKkuMxMR9pH9HDoppVGHNarczd353RbBhkE_dkm-TagQAnvbTyD0jbcTWVJTIasT--ALI0J-dALO2Xi4OG-RKvVY3FLSQWyT3YezRsxtFzO89XFltH5kNpojLqNIQ6yo9vRLq9XhC6qZc9f5B-va00l9lYj9TU73t8Dv4xU2V4-GWoehDuDR9qy_q2fZphb_1x5ErEv6z5o2jhSlKugIS7KhGE";
}
//        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);