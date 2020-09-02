package com.his.android.Activity.UploadMultipleImg;

public class ApiUtilsForNonMedicos {

    private ApiUtilsForNonMedicos() {}

    /*
     * BaseUrl of this application
     * */

    //Live Url
   public static final String BASE_URL = "http://182.156.200.178:121/";


    //Testing Url
    //public static final String BASE_URL = "http://182.156.200.178:108/";


    public static Api getAPIService() {

        return RetrofitClientForFile.getClient(BASE_URL).create(Api.class);

    }
}