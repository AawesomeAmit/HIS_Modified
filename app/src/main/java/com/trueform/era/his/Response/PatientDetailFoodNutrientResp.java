package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.NutrientFoodResponseValue;

public class PatientDetailFoodNutrientResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public NutrientFoodResponseValue nutrientFoodResponseValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public NutrientFoodResponseValue getNutrientFoodResponseValue() {
        return nutrientFoodResponseValue;
    }
}
