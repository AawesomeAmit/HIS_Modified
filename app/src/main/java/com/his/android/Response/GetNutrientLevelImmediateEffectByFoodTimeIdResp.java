package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.GetNutrientLevelImmediateEffectByFoodTimeId;

import java.util.List;


public class GetNutrientLevelImmediateEffectByFoodTimeIdResp {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<GetNutrientLevelImmediateEffectByFoodTimeId> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<GetNutrientLevelImmediateEffectByFoodTimeId> getResponseValue() {
        return responseValue;
    }
}
