package com.his.android.Response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NutriAnalyzerResp {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<NutriAnalyzerDataRes> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<NutriAnalyzerDataRes> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<NutriAnalyzerDataRes> responseValue) {
        this.responseValue = responseValue;
    }


    /*@SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<NutriAnalyze> nutriAnalyzeValue =null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<NutriAnalyze> getNutriAnalyzeValue() {
        return nutriAnalyzeValue;
    }
*/
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
