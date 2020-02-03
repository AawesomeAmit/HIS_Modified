package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.NutriAnlzrFoodListDtlList;

import java.util.List;

public class NutriAnlzrFoodListDtlRes {
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseValue")
    @Expose
    public List<NutriAnlzrFoodListDtlList> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<NutriAnlzrFoodListDtlList> getResponseValue() {
        return responseValue;
    }
}
