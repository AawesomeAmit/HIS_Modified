package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DiseaseList;
import com.trueform.era.his.Model.GetNutrientByPrefixTextModel;

import java.util.List;

public class GetNutrientByPrefixTextResp {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<GetNutrientByPrefixTextModel> responseValue = null;

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

    public List<GetNutrientByPrefixTextModel> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<GetNutrientByPrefixTextModel> responseValue) {
        this.responseValue = responseValue;
    }
}
