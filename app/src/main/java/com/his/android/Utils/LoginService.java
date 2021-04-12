package com.his.android.Utils;

import com.his.android.Response.IcuPatientListResp;
import com.his.android.Response.RecepientListResp;
import com.his.android.Response.SubHeadIDResp;
import com.his.android.Response.SubjectNameExistResp;
import com.his.android.Response.SubjectNameTabResp;
import com.his.android.Response.SubjectWiseChatResp;

import org.json.JSONArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("Dashboard/GetsubDepertmentByHID")
    Call<SubHeadIDResp> basicLogin(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("headID") String headID, @Field("who") Integer who);



    @FormUrlEncoded
    @POST("Dashboard/GetICUPatientList")
    Call<IcuPatientListResp> getICUPatientList(@Header("accessToken") String accessToken, @Header("userID") String userID, @Field("subDeptID") Integer subDeptID, @Field("who") Integer who, @Field("headID") Integer headID);


    @FormUrlEncoded
    @POST("ChatMessage/GetDepartmentDesignationUserList")
    Call<RecepientListResp> getDepartmentDesignationUserList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("preIndex") String preIndex);


    @FormUrlEncoded
    @POST("ChatMessage/CreateNewChatMessage")
    Call<ResponseBody> createNewChatMessage(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID1,
            @Field("pid") Integer pid,
            @Field("chatSubjectName") String chatSubjectName,
            @Field("subjectId") Integer subjectId,
            @Field("userId") String userId,
            @Field("recipientList") JSONArray recipientList,
            @Field("chatMessage") String chatMessage,
            @Field("timelineMessage") Integer timelineMessage,
            @Field("filePath") JSONArray filePath);


    @FormUrlEncoded
    @POST("ChatMessage/CreateRecipientChatMessage")
    Call<ResponseBody> createRecipientChatMessage(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID1,
            @Field("chatId") Integer chatId,
            @Field("chatMessage") String chatMessage,
            @Field("timelineMessage") Integer timelineMessage,
            @Field("userId") String userId,
            @Field("filePath") JSONArray filePath);

    @FormUrlEncoded
    @POST("ChatMessage/GetSubjectNameforTabsPatientWise")
    Call<SubjectNameTabResp> getSubjectNameforTabsPatientWise(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID1,
            @Field("pid") Integer pid,
            @Field("UserId") String userId);

    @FormUrlEncoded
    @POST("ChatMessage/GetSubjectNameWiseChatMessagesList")
    Call<SubjectWiseChatResp> getSubjectWiseChatList(@Header("accessToken") String accessToken, @Header("userID") String userID1, @Field("chatId") Integer chatId, @Field("UserId") String userId, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("ChatMessage/GetSubjectnameAlreadyExist")
    Call<SubjectNameExistResp> getSubjectnameAlreadyExist(
            @Header("accessToken") String accessToken,
            @Header("userID") String userID1,
            @Field("pid") Integer pid,
            @Field("chatSubjectName") String chatSubjectName);
}
