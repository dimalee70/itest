package itest.kz.util;

public interface Constant
{

//    eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjRjNWUyNDg4ZGYyMWEwYzQzNDY4NGU0MGE4ZWM4NzE5MjEzYTJlMzkyZDIxMjgwMDQ5YjU4ZTFiYWQyMDhmYTc1N2E1NjNiNTgzOTZjYzliIn0.eyJhdWQiOiIxIiwianRpIjoiNGM1ZTI0ODhkZjIxYTBjNDM0Njg0ZTQwYThlYzg3MTkyMTNhMmUzOTJkMjEyODAwNDliNThlMWJhZDIwOGZhNzU3YTU2M2I1ODM5NmNjOWIiLCJpYXQiOjE1NTMxNDM4OTcsIm5iZiI6MTU1MzE0Mzg5NywiZXhwIjoxNTg0NzY2Mjk3LCJzdWIiOiIxMjEyNTIwIiwic2NvcGVzIjpbXX0.RkuwHI7yBRMEsYFMqTNLB33KJKA9WDEe9rfn06J-Oljhm6HCFA1s3lleHVvhJq4Yfuu4V-774F19mavPCYYqeAPe07qzAOm3atPNJZlp5JhuDAKMaWQRbTPyVS0qRoNP9251tT0qUBaWfH5-NbG8LFWpyfUdDP5VAVqYtRXhBc6ils6WoZiOr193mZc6cPn8wJY__e-nqcuwz0yuYWQKfTCIsUIVfoXasm8CUC7H6hRnnfMZAy9wsMEIfur3o4bPp8O8Flh0Gln9s3CRCCF3WN9eWc8gereHLiMFXOnLTKtjPdxQweYm0gdyPiIoS3Ub91mWnbDDEN3J815oLFLw-0-CrYGX0ISDrZacgxMF9T5CEpcre9KhjdLlD4daemqk6lyn2ky1wF-7lIyhrQLTdIxV9RKTFo5DV-8eISpNHxm2CQ1dlKtjH0oLarU5FkTIRxCuC8HEUZltwBbgSVNb0fb42-skh7TBGKdaeicsJ5jui9wjAC2MxMUyiP6bDfe7yuQJ7SSuSl1jL9v9mZ8Dy0pDx8OBK-FxlnijfPdZ96G19E4m47J5gJVtWhRhGlJSC6S2Y_NOoPN8ALg2pVihBAIUxhBOqI_PZk017uokDte2GwkylDGisodxFx0JD9zr2O1tfW_hmp1BQC-bWyDI0cjH6Bef9v78AxEb0zYRdfg

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

    public final  String BASE_URL = "http://dev.itest.kz/";

    public final String ACCEPT = "application/json";

    public final String ATTESTATION = "attestation";

    public final String SELECTED_LECTURE_RESPONSE = "SELECTED_LECTURE_RESPONSE";

    public final String ENT = "ent";

    public final String HTML = "text/html; charset=utf-8";

    public final String UTF_8 = "UTF-8";

    public final String SELECTED_SUBJECT = "SELECTED_SUBJECT";

    public final String SELECTED_NODE = "SELECTED_NODE";

    public final String MATHJAX = "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-MML-AM_CHTML' async></script>";

    public final String ACCESSTOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjNkMzkzNGEyODAwZjcwOWM5MzM4YjAwYmVkODc5M2NkNzZiZTU4Y2QxMmM2ZjQ0ZTliOTkwYWQwMDViOTQzZmYzMGMxNDIyYTQ3YTI0OGUyIn0.eyJhdWQiOiIxIiwianRpIjoiM2QzOTM0YTI4MDBmNzA5YzkzMzhiMDBiZWQ4NzkzY2Q3NmJlNThjZDEyYzZmNDRlOWI5OTBhZDAwNWI5NDNmZjMwYzE0MjJhNDdhMjQ4ZTIiLCJpYXQiOjE1NTExNTgxNTAsIm5iZiI6MTU1MTE1ODE1MCwiZXhwIjoxNTgyNjk0MTUwLCJzdWIiOiIxMjEyNTIwIiwic2NvcGVzIjpbXX0.s3hCvWgMIxmt_xY-OvF-fac5EwHtGLw5KC4AMy38GkyLX2rgOWvMgHSPEUjXfRMYhf2TrvK73BI6P4M2tNids352prM1_KGja7d05Y34zCvnNCgc_VmO_Hksvpjy-P32tQ7w8yHG3y6tL8wZ3xZrI-zZXZua8j_LS6FM_Vrgujx-f_eq34hQ99Uy6MZQvWLeOdk439hCnJpEzh5uKvPdA_xPdSgfbiDyJvfDekuCGjqHk_AX8JHEvrDi5O6vwikioNlc9hWswXnECbtX6yHDScaFxB-7gcOgObOB7fI3Xl6idTmgQn8Hhepnbhe0ItKjJLWyuTiWs0QiDS-s7W0rVXGo1kbonpXmELGkFUsSRxr5cpBNcJRVO6VrpQZgQV69OgOzUtVeT9AU9ljkViOu6xK7-JdosLjG29BQb-wM6So4ERqjZWD_GId17IEZyAaUXRgRd_T3e6ogWolSi16IH5CNCmZNZp8SeHwW4iXWX_iWzzBFgwv7yQA0UUfcTui9Ka8bOf9CJM0dzIMSETImwuJkEvBCRtQsDcFSOPYcftWK_fhfcYtXi92-bJ_QUXEihNO4zAByW2JOx02RBB8mrTRZ-3r8QlHlwQUEkG0AUH5FVklGKio7nK_CfN0-InQLlXDDWJ-qZOfBUaeywWzFu3qMTrYDficJrpkEdyCva7k";
}
