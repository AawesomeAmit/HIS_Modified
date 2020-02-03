package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ControlList;
import com.trueform.era.his.Model.GetNutrientAverageDeficiencyModel;
import com.trueform.era.his.Model.Result;

import java.util.List;

public class GetNutrientAverageDeficiencyResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<GetNutrientAverageDeficiencyModel> getResponseValue() {
        return responseValue;
    }

    @SerializedName("responseValue")
    @Expose
    public List<GetNutrientAverageDeficiencyModel> responseValue = null;
}
