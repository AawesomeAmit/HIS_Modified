package com.trueform.era.his.Activity.UploadMultipleImg;

public class ApiUtils {

    private ApiUtils() {}

    /*
     * BaseUrl of this application
     * */


    //Live Url For Apk
   public static final String BASE_URL = "http://182.156.200.178:229/";

    //Testing Url
    //public static final String BASE_URL = "http://182.156.200.178:108/Api/";


    public static com.trueform.era.his.Activity.UploadMultipleImg.Api getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(Api.class);

    }
}