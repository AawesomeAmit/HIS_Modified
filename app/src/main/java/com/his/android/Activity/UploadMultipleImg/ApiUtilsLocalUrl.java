package com.his.android.Activity.UploadMultipleImg;

public class ApiUtilsLocalUrl {

    private ApiUtilsLocalUrl() {}

    /*
     * BaseUrl of this application
     * */


    //Live Url For Apk
//   public static final String BASE_URL = "http://182.156.200.178:229/";
    public static final String BASE_URL = "http://182.156.200.179:201/";

    //Testing Url
    //public static final String BASE_URL = "http://182.156.200.178:108/Api/";


    public static Api getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(Api.class);

    }
}