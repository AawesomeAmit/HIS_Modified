package com.trueform.era.his.Activity.UploadMultipleImg;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    //Add Self Assign no repeat task
    @Multipart
    @POST("UploadPatientDocument.ashx")
    Call<String> addImage(
            @Header("accessToken") String accessToken,
            @Header("userID") String userId,
            @Part MultipartBody.Part[] fileName,
            @Part("PID") RequestBody PID,
            @Part("userID") RequestBody userID,
            @Part("fileDateTime") RequestBody fileDateTime

    );

}
