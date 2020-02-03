package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AttributeValueList;

import java.util.List;

public class GetIdealNutrientIntakeResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public GetIdealNutrientIntakeDataResp responseValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public GetIdealNutrientIntakeDataResp getResponseValue() {
        return responseValue;
    }
}
